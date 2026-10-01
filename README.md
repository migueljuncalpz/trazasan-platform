# TrazaSan Platform

Backend de TrazaSan organizado como monorepo de microservicios.

## Servicios

| Servicio | Puerto | Responsabilidad |
| --- | ---: | --- |
| `api-gateway` | 8080 | Punto de entrada del backend |
| `catalog-service` | 8081 | Unidades, residuos y códigos LER |
| `traceability-service` | 8082 | Contenedores y trazabilidad |
| `operations-service` | 8083 | Retiradas e incidencias |
| `reporting-service` | 8084 | Indicadores e informes |

El repositorio contiene únicamente el esqueleto técnico. Todavía no incluye lógica de negocio ni endpoints.

## Comandos iniciales

```bash
docker compose up -d
./mvnw clean verify
```
