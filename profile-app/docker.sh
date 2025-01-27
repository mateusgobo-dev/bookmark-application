docker run --name postgresql \
-p 5432:5432 \
-e POSTGRES_DB=profile-db \
-e POSTGRES_USER=sa \
-e POSTGRES_PASSWORD=123 \
-e PGDATA=/var/lib/postgresql/data/pgdata \
-v /containers/postgres/mount:/var/lib/postgresql/data \
-it postgres