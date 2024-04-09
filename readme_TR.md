# kredinbizde (DefineX Final Project)

## Proje Açıklaması

Bu proje, kredi sektöründe faaliyet gösteren bankalar arasında ortak bir veritabanını kullanarak, müşteri bilgilerini çekmek ve kredi kartı veya kredi başvurularını işlemek amacıyla geliştirilmiştir. Merkezi bir veritabanı ve Akbank gibi servisler aracılığıyla bankalar, müşteri bilgilerine erişebilir ve başvuruları işleyebilirler. Proje, gateway üzerinden yönlendirme yapar, arka planda API'ler aracılığıyla iletişim kurar ve MySQL veritabanını kullanır. "kredinbizde-service" ve "akbank-service" için gerekli olan unit testler başarıyla çalışmaktadır.

### Projeyi Çalıştırma Adımları

1. "kredinbizde-service" ve "akbank-service" dizinlerindeki docker-compose dosyalarını çalıştırarak gerekli veritabanı ve servislerin kurulumunu gerçekleştirin.
2. İlk olarak, "kredinbizde-discovery" projesinin Euroka sunucusunu çalıştırın.
3. "kredinbizde-gw" (gateway) projesini çalıştırın.
4. "kredinbizde-service" projesini çalıştırın. Veritabanları ve gerekli servislerin hazır ve çalışıyor olduğundan emin olun.
5. "akbank-service" projesini çalıştırın.
6. İsteğe bağlı olarak, "notification-service" projesini çalıştırın. (Notification service, rabbitMQ tarafından gelen mesajları alan bir tüketici projedir.)
7. Projeler sorunsuz çalışıyorsa, Postman'a aktarabileceğiniz örnek sorgular içeren bir JSON dosyası ile sorguları gerçekleştirebilirsiniz.

#### Notlar

- "akbank-service" içindeki sorguların doğru çalışabilmesi için, "kredinbizde" veritabanında bulunan "banks" tablosundaki Akbank'ın ID'sinin, "akbank-service" projesinde manuel olarak ayarlanması gerekmektedir. Varsayılan ID değeri 2'dir. Yani, Akbank'ın ID'si veritabanında 2 değilse, sorgular yanlış çalışabilir veya hata alabilirsiniz.
- delete user özelliği sadece akbank-service'deki unit test için yaratılan objelerin silinmesi için eklenmiştir. Herhangi bir uygulamada (özellikle önemli ve canlı projelerde) veritabanından silme özelliği yerine, active boolean'ı tutmak ve onu değiştirmek daha doğru olacaktır.