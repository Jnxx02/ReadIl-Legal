# ReadIl-Legal V.1.0

ReadIl-Legal adalah aplikasi database komik dengan menggunakan API dari MangaDex untuk menyediakan berbagai macam komik kepada pengguna. Aplikasi ini dirancang untuk memberikan "kemudahan" untuk menemukan data komik.

## Fitur Utama
- **Daftar Komik:** Menampilkan berbagai macam komik mulai dari yang populer hingga yang tidak populer sama sekali.
- **Detail Komik:** Lihat informasi detail dari komik-komik yang menarik bagi Anda.
- **Bookmark:** Simpan komik favorit Anda untuk dibaca nanti.
- **Update:** Pengoptimalan serta pembaruan yang akan dilakukan secara berkala.

## Cara Penggunaan
1. **Get Started:** Aplikasi ini mempermudah pengguna karena tidak memerlukan autentikasi apapun seperti Login atau Register.
2. **Daftar Komik:** Fitur ini sudah tersedia secara langsung di halaman utama tanpa perlu effort untuk mencarinya.
3. **Detail Komik:** Klik pada komik yang diinginkan untuk mulai menampilkan data-data mengenai komik tersebut.
4. **Bookmark:** Simpan komik ke daftar bookmark untuk akses cepat di lain waktu.

## Implementasi Teknis
1. **Home:**
   - Menggunakan RecyclerView untuk menampilkan daftar komik.
   - Menggunakan CardView untuk membuat desain card.
   - Penggunaan Picasso untuk pengambilan gambar sampul komik dari API.
2. **Tambahkan ke Bookmark:**
   - Menggunakan SharedPreference untuk menyimpan daftar komik yang ditandai sebagai "favorites".
3. **Daftar Bookmark:**
   - Menggunakan SharedPreference untuk mengambil daftar komik yang sudah ditandai sebagai "favorites".
   - Menggunakan RecyclerView untuk menampilkan daftar komik.

## Teknologi yang Digunakan
- **AndroidStudio Java:** Untuk pengembangan aplikasi Android.
- **Retrofit:** Untuk mengakses koneksi internet dan pengambilan data dari API
- **Picasso:** Untuk memuat dan penangan gambar.
- **SharedPreference:** Untuk menyimpan data.
- **CardView:** Untuk menampilkan data dalam bentuk card guna menambah estetika.

## Author
[Jnxx02](https://github.com/Jnxx02)

## Referensi
- [MangaDex API Documentation](https://api.mangadex.org/docs/)
- [Color Pallette]([https://nodejs.org/en/docs/](https://colorhunt.co/palette/2650732d95969ad0c2f1fada))
- [Webtoon]([https://expressjs.com/](https://www.webtoons.com/id/))
- [MangaDex]([https://reactjs.org/docs/getting-started.html](https://mangadex.org))

## Preview Aplikasi
Lihat tangkapan layar aplikasi di [halaman screenshot](screenshot_page_url).

