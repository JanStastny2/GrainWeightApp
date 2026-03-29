# GrainWeightApp

A System Under Test (SUT) for benchmarking Java concurrency strategies. Simulates an agricultural grain-weighing station while exposing a controllable concurrency engine targeted by [LoadTesterApp](../LoadTesterApp).

## Architecture

The application uses Spring Boot MVC with three concurrency processing strategies:

| Mode | Mechanism | Description |
|------|-----------|-------------|
| `SERIAL` | Single-permit `Semaphore` | Strictly one request processed at a time |
| `POOL` | Fixed `ThreadPoolExecutor` | Platform thread pool (core count × 2) with optional cap |
| `VIRTUAL` | `Executors.newVirtualThreadPerTaskExecutor()` | Java 21 virtual threads with optional cap |

Every response from the `/api/work/**` endpoints wraps the payload in `ApiResponse<T>` containing:
- `durationMs` — total wall-clock time (client-observed)
- `serverProcessingMs` — time spent inside the strategy executing the task
- `queueWaitMs` — time waiting for a semaphore permit

These fields are the core benchmark data consumed by LoadTesterApp.

## Key Endpoints

### Work Endpoints (load-testable)

```
GET  /api/work/records?mode=SERIAL|POOL|VIRTUAL&size={cap}&delayMs={ms}
POST /api/work/records?mode=SERIAL|POOL|VIRTUAL&size={cap}&delayMs={ms}
GET  /api/work/users?mode=SERIAL|POOL|VIRTUAL&size={cap}&delayMs={ms}
```

Parameters:
- `mode` — concurrency strategy (default: `SERIAL`)
- `size` — pool size or semaphore cap (optional; ignored for SERIAL)
- `delayMs` — artificial I/O delay in milliseconds via `Thread.sleep()` (default: 0)

### Response Format

```json
{
  "status": 200,
  "durationMs": 152,
  "serverProcessingMs": 101,
  "queueWaitMs": 48,
  "data": [...],
  "message": "records: mode=POOL size=4 delayMs=100"
}
```

### Actuator Endpoints (required by LoadTesterApp)

```
GET /actuator/metrics/system.cpu.usage
GET /actuator/metrics/jvm.memory.used?tag=area:heap
GET /actuator/metrics/jvm.memory.used?tag=area:nonheap
GET /actuator/health
GET /actuator/prometheus
```

### CRUD Endpoints

```
GET/POST /api/records/**    — WeightRecord management
GET/POST /api/fields/**     — Field management
GET/POST /api/drivers/**    — Driver management
GET/POST /api/users/**      — User management
```

### UI (Thymeleaf)

```
/weightrecords   — Weight record list and forms
/fields          — Field list and forms
/drivers         — Driver list and forms
/users           — User management (ADMIN role required)
/login           — Login page
```

## Configuration

Key settings in `src/main/resources/application.properties`:

```properties
server.port=8081
spring.datasource.url=jdbc:h2:~/db2
spring.jpa.hibernate.ddl-auto=create-drop   # Schema recreated on restart; seed data re-inserted
management.endpoints.web.exposure.include=health,info,metrics,prometheus
```

> **Note:** The database uses `create-drop` — all data is lost on restart. This is intentional; `benchmark_runner.py` restarts the app between benchmark iterations for a clean state.

## Seed Data

On startup, the application seeds:
- Users: `admin` / `heslo` (role: ADMIN), `user` / `heslo` (role: USER)
- 2 drivers, 2 fields

## Build and Run

Requirements: Java 21, Maven 3.9+

```bash
mvn clean install
java -jar target/grain-weight-app-0.0.1-SNAPSHOT.jar
```

Or with Maven wrapper (if `.mvn/wrapper/` is present):
```bash
./mvnw spring-boot:run
```

Application starts on **port 8081**.

## Running Tests

```bash
mvn test
```

Test coverage includes:
- Unit tests for `SerialStrategy`, `PoolStrategy`, `VirtualStrategy` (concurrency and cap enforcement)
- Integration test for `WorkController` (`/api/work/records` with all three modes)
- Unit tests for `UserService`
- Repository test for `UserRepository`
