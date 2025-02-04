docker run --rm  --name customer-db \
-p 5432:5432 \
-e POSTGRES_DB=profile-db \
-e POSTGRES_USER=sa \
-e POSTGRES_PASSWORD=123 \
-e PGDATA=/var/lib/postgresql/data/pgdata \
-v /home/containers/customer-db/mount:/var/lib/postgresql/data \
-d postgres