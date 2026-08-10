# Loan EMI Scheduler — JWT Security Flow

This ports the JWT auth flow from `Project-demo-master` (Spring-security reference)
onto the `entity` / `enums` model for this project, extended to 3 roles.

## Role mapping

Your `enums.Role` enum defines:

| Enum value     | Meaning in this app        |
|-----------------|-----------------------------|
| `BORROWER`      | the **User** role            |
| `LOAN_OFFICER`  | the **loan_manager** role    |
| `ADMIN`         | the **Admin** role           |

(`User`, `loan_manager`, `admin` don't exist as literal enum constants — the
actual constants are `BORROWER`, `LOAN_OFFICER`, `ADMIN`. If you'd rather the
enum itself used those exact names, rename the constants in `Role.java` and
this whole flow keeps working unchanged, since every class reads the role
off the enum rather than hardcoding strings.)

## What changed vs. the reference project

- Reference used a `username` + separate `Role` **entity/table**. Your `User`
  entity already has `email` + an **embedded `Role` enum**
  (`@Enumerated(EnumType.STRING)`), so there's no `RoleRepository`/`Role`
  entity here — the JWT/`UserDetailsService` read the enum directly.
- Reference's public `/register` accepted a `role` string from the client
  (anyone could register as any role). That's a privilege-escalation hole,
  so here:
  - `POST /api/auth/register` (public) **always** creates a `BORROWER`.
  - `POST /api/admin/staff` (ADMIN-only) creates `LOAN_OFFICER` or `ADMIN`
    accounts.
  - `AdminSeeder` creates one default `ADMIN` on first boot (see
    `application.properties`) so there's a way to bootstrap the first admin
    — log in with it once, then create real admins/loan officers and stop
    using the seeded one.
- Debug `System.out.println`s from the reference filter were dropped.
- Added a 403 handler (`AccessDeniedException`) alongside the 401 handler,
  since with 3 roles "wrong role" (403) is now a distinct, common case from
  "no/bad token" (401).

## The flow, end to end

1. **Register (User / borrower)** — `POST /api/auth/register` `{email, password}`
   → `201`, account created with role `BORROWER`. No token yet.
2. **Log in** — `POST /api/auth/login` `{email, password}`
   → `200` with `{accessToken, tokenType, role}`. Works for all 3 roles.
3. **Call a protected endpoint** — send `Authorization: Bearer <accessToken>`.
   `JwtAuthenticationFilter` validates the token and loads a
   `UserDetails` whose single authority is `ROLE_<enum name>` (e.g.
   `ROLE_BORROWER`).
4. **Authorization is enforced two ways**, both driven off the same enum:
   - URL-pattern rules in `SecurityConfig` (`/api/user/**` → `BORROWER`,
     `/api/loan-manager/**` → `LOAN_OFFICER`, `/api/admin/**` → `ADMIN`).
   - `@EnableMethodSecurity` is on, so you can additionally annotate any
     method with `@PreAuthorize("hasRole('ADMIN')")` for finer-grained
     control than the URL pattern gives you.
5. **Bootstrapping staff** — an `ADMIN` calls `POST /api/admin/staff`
   `{email, password, role: "LOAN_OFFICER"}` (or `"ADMIN"`) to create the
   other two role types. There is no public way to become staff.

## Endpoint summary

| Method | Path                          | Who               |
|--------|--------------------------------|-------------------|
| POST   | `/api/auth/register`           | Public            |
| POST   | `/api/auth/login`               | Public            |
| GET    | `/api/user/profile`             | BORROWER          |
| GET    | `/api/user/loans`                | BORROWER          |
| GET    | `/api/loan-manager/loans/pending`| LOAN_OFFICER      |
| PUT    | `/api/loan-manager/loans/{id}/decision` | LOAN_OFFICER |
| POST   | `/api/admin/staff`               | ADMIN             |
| GET    | `/api/admin/users`                | ADMIN             |
| PUT    | `/api/admin/users/{id}/deactivate`| ADMIN             |

## Running it

1. Create a Postgres DB matching `application.properties`
   (`jdbc:postgresql://localhost:5432/loan_emi_schedular`), or point it at
   your own instance.
2. `./mvnw spring-boot:run` (add the maven wrapper jar, or use your local
   `mvn`, since the wrapper scripts weren't copied over).
3. On first boot, an admin is seeded — see the console log line and
   `app.admin.default-*` in `application.properties`.
4. Log in as that admin, create a `LOAN_OFFICER`, register a `BORROWER`,
   and try hitting each other's endpoints to see the 403s.

## Not included (out of scope for the security flow)

Business logic for actually applying for/approving loans, generating EMI
schedules, OTP verification, KYC, document upload, and payments — the
`Loan`, `Emi`, `Payment`, `Otp`, `Document`, etc. entities are already in
your model and wired for JPA, but their services/controllers aren't built
here. The controllers above are role-gated stubs to demonstrate the access
pattern; swap the placeholder bodies for real logic as you build those
features.
