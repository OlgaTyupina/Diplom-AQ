## Дипломный проект
[План автоматизации тестирования](https://github.com/OlgaTyupina/Diplom-aqa/blob/master/Plan.md)
**Запуск SQL**
1. Запустить контейнеры docker-compose -f docker-compose-mysql.yml up –d.
2. Запустить приложение java -jar artifacts/aqa-shop.jar.
3. Запустить тесты:gradlew test.
4. Остановить контейнеры: docker-compose -f docker-compose-mysql.yml down.

**Примечание**
Тестирование проводилось на ПК под управлением ОС Windows 10 Home, следует обратить внимание на написание сетевых адресов в файле "application.properties", где вместо "localhost:" прописано "192.168.99.100:", что является IP-адресом виртуальной машины, на которой работает Docker
