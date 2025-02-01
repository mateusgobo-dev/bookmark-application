docker run --rm --name rabbitmq \
  -p 5672:5672 \
  -p 15672:15672 \
  -v /home/containers/rabbitmq:/var/lib/rabbitmq \
  -v ./rabbitmq/rabbitmq.conf:/etc/rabbitmq/rabbitmq.conf \
  -v ./rabbitmq/conf.d:/etc/rabbitmq/conf.d \
  -d rabbitmq:3.8.0-management
echo 'Waiting container finish starting...'
sleep 5
docker exec -it rabbitmq rabbitmqctl add_user magalu magalu
docker exec -it rabbitmq rabbitmqctl set_permissions -p / magalu ".*" ".*" ".*"
docker exec -it rabbitmq rabbitmqctl set_user_tags magalu administrator

docker exec -it rabbitmq rabbitmqctl add_user mateusgobo mateusgobo
docker exec -it rabbitmq rabbitmqctl set_permissions -p / mateusgobo ".*" ".*" ".*"
docker exec -it rabbitmq rabbitmqctl set_user_tags mateusgobo administrator