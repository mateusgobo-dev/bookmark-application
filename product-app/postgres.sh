docker run --rm  --name product-db \
-p 5433:5432 \
-e POSTGRES_DB=product-db \
-e POSTGRES_USER=sa \
-e POSTGRES_PASSWORD=456 \
-e PGDATA=/var/lib/postgresql/data/pgdata \
-v /containers/postgres/mount:/var/lib/postgresql/data \
-d postgres