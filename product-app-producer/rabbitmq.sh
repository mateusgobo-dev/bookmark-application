docker run --rm  --name rabbitmq \
 -e RABBITMQ_DEFAULT_USER=admin \
 -e RABBITMQ_DEFAULT_PASS=admin \
 -p 5672:5672 \
 -p 15672:15672 \
 -v /home/rabbitmq:/var/lib/rabbitmq \
 -d rabbitmq:4.0.5-management
