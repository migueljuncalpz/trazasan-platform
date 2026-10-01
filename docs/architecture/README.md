# Arquitectura

Cada microservicio es una aplicación Spring Boot desplegable de forma independiente.

- Un servicio no accede directamente a las tablas de otro servicio.
- No se comparten entidades JPA entre servicios.
- Los contratos HTTP se documentarán en `contracts/openapi`.
- Los contratos de eventos se documentarán en `contracts/events`.
- PostgreSQL comparte servidor solo durante el desarrollo local; cada servicio usa una base de datos distinta.
