#Akıllı Labirent Çözücü
Bu proje, labirent çözme algoritmalarını (BFS, DFS, ve Greedy Best First Search) Java kullanarak implement eden bir uygulamadır. Kullanıcı, çeşitli formatlarda yüklenebilen labirentleri çözebilir ve çözümleri görsel olarak izleyebilir. JavaFX kullanarak GUI (grafiksel kullanıcı arayüzü) desteği sağlanmıştır.


#Özellikler
BFS (Breadth First Search) algoritması ile garantili en kısa yolu bulma
DFS (Depth First Search) algoritması ile labirent çözme
Greedy Best First Search algoritması ile çözüm
Kullanıcı dostu JavaFX GUI desteği
Resim ve metin dosyalarından labirent yükleme (JPG, PNG, TXT formatları)

#Teknolojiler ve Araçlar
Java 8+
JavaFX (GUI için)
JUnit (Testler için)
Min-Heap Priority Queue (Labirent çözme algoritmalarında sıralama yapmak için)

#Kurulum ve Çalıştırma

1. Adım: Depoyu Klonlama
Bu projeyi bilgisayarınıza klonlamak için aşağıdaki komutu terminal veya komut istemcisine yazın:
git clone https://github.com/kullaniciadi/labirent-cozucu.git

2. Adım: Bağımlılıkları Yükleyin
Projeyi çalıştırabilmek için Maven kullanıyorsanız, terminalde şu komutu kullanarak bağımlılıkları yükleyin:
mvn install

3. Adım: Uygulamayı Çalıştırma
Projeyi çalıştırmak için Java IDE'nizde projeyi açın veya terminalde şu komutla çalıştırın:
java -jar LabirentCozucu.jar

#Kullanım
Labirent Yükle: Labirenti JPG, PNG veya TXT dosya formatından yükleyebilirsiniz.
Çözüm Algoritması Seçin: BFS, DFS veya Greedy Best First Search algoritmalarından birini seçin.
Çözümü Görüntüle: Seçilen algoritma ile labirent çözülür ve çözüm yolu görsel olarak gösterilir.

#GUI Özellikleri:
Labirent Yükle butonu ile labirent dosyasını seçebilirsiniz.
BFS ile Çöz, DFS ile Çöz ve Greedy Best First Search ile Çöz butonları ile farklı algoritmalarla çözüm yapabilirsiniz.
Labirentin başlangıç (S) ve bitiş (E) noktaları görsel olarak işaretlenir.

#Algoritmalar
BFS (Breadth First Search)
BFS algoritması, labirentteki en kısa yolu bulmak için kullanılır. En kısa yolun garanti edilmesi amacıyla her adımda tüm komşular kontrol edilerek ilerlenir.

DFS (Depth First Search)
DFS, bir yol bulana kadar labirentin derinliklerine inmeyi tercih eder. Bu algoritma her zaman en kısa yolu garanti etmez.

Greedy Best First Search
Bu algoritma, her adımda hedefe en yakın olan yolu seçer ve çözüm bulmaya çalışır. Fakat her zaman en kısa yolu garanti etmez.
