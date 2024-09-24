# Tarım Traktörleri için Otomatik Dümenleme Sistemi

Bu proje, Dornmarket'teki Android Developer stajım sırasında geliştirilmiş bir tarım traktörleri için otomatik dümenleme sistemi uygulamasıdır. Proje, tarım alanında otomatik yönlendirme sağlamak amacıyla tasarlanmıştır.

Proje kapsamında Android Studio üzerinde Java dilini kullanarak, kullanıcı dostu bir arayüz tasarımı gerçekleştirdim ve Navigation Component ile sayfa geçişlerini optimize ettim. Ayrıca, gezinme deneyimini daha zengin hale getirmek için Navigation Drawer ekledim. Veritabanı yönetimi için SQLite kullanarak verilerin güvenli ve hızlı bir şekilde depolanmasını sağladım. Kullanıcı deneyimini genişletmek amacıyla uygulamaya Türkçe ve İngilizce dil desteği ekledim.

Bu projede MVVM (Model-View-ViewModel) mimarisi kullanarak daha modüler, sürdürülebilir ve yönetilebilir bir kod yapısı oluşturdum. LiveData ile verilerin kullanıcı arayüzüyle dinamik bir şekilde senkronize olmasını sağladım ve RecyclerView kullanarak verilerin liste görünümünde verimli bir şekilde gösterilmesini sağladım. Kod yapısını daha temiz ve bağımlılıkları yönetilebilir kılmak için Dagger Hilt ile Dependency Injection prensibini uyguladım.

## Kullanılan Teknolojiler

- **Android Studio**
- **Java**
- **Navigation Component**: Sayfa geçişlerini optimize etmek için kullanıldı.
- **Navigation Drawer**: Kullanıcı deneyimini zenginleştirmek için entegre edildi.
- **SQLite**: Veritabanı yönetimi için kullanıldı.
- **MVVM (Model-View-ViewModel)**: Modüler ve yönetilebilir bir kod yapısı oluşturmak için uygulandı.
- **LiveData**: Verilerin UI ile dinamik senkronizasyonunu sağladı.
- **RecyclerView**: Verilerin liste görünümünde gösterilmesi için kullanıldı.
- **Dagger Hilt**: Bağımlılık yönetimi için kullanıldı.
- **Google Maps API**: Traktörlerin gerçek zamanlı konumlarını harita üzerinde görüntülemek için entegre edildi.
- **Lokasyon İşlemleri**: Kullanıcılara tarım alanında doğru ve güncel konum bilgileri sunmak için kullanıldı.

## Projenin Kazandırdıkları

- **MVVM ve LiveData** ile çalışma deneyimi, uygulama geliştirme sürecinde verilerin yönetimini ve UI güncellemelerini daha verimli hale getirdi.
- **Dependency Injection (Dagger Hilt)** kullanarak kodun daha esnek ve yeniden kullanılabilir olmasını öğrendim; bu da büyük ölçekli projelerde modülerliği artırdı.
- **Google Maps API** ve lokasyon işlemleri ile çalışma deneyimi, gerçek zamanlı verilerin haritalarla entegrasyonu konusunda derinlemesine bilgi sağladı.
- **SQLite** ile veritabanı işlemleri yaparak yerel veri yönetimi ve performans optimizasyonu konusunda tecrübe kazandım.

Bu proje, mobil uygulama geliştirme becerilerimi ilerleterek, kompleks projelerde daha etkili ve verimli çözümler sunmamı sağladı.
