
# Binary Tree Visualizer 🌳

![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg) ![Java](https://img.shields.io/badge/Java-11%2B-blue.svg)

Aplikasi desktop yang interaktif dan intuitif, dibuat dengan **Java Swing**, untuk memvisualisasikan dan memahami struktur data **Binary Search Tree (BST)**. Sangat cocok sebagai alat bantu belajar bagi mahasiswa, pengajar, atau siapa pun yang tertarik dengan algoritma dan struktur data.



---

## Fitur Utama

* **Visualisasi Real-time**: Lihat struktur pohon tergambar secara otomatis saat Anda menambah, menghapus, atau mengubah data.
* **Operasi Tree Lengkap**: Lakukan semua operasi dasar BST: `Insert`, `Delete`, `Search`, dan `Update` melalui antarmuka grafis.
* **Dukungan Multi-Tipe Data**: Bekerja dengan tipe data `Integer`, `Float`, `String`, dan `Character`.
* **Berbagai Metode Traversal**: Tampilkan hasil traversal pohon dengan metode `In-order`, `Pre-order`, `Post-order`, dan `Level-order`.
* **Kustomisasi Tampilan**: Personalisasi pengalaman Anda dengan mengubah warna latar belakang, node, teks, dan garis.
* **Analisis Statistik**: Dapatkan wawasan tentang pohon, termasuk **tinggi pohon**, **jumlah total node**, **jumlah daun**, dan **pengecekan keseimbangan** (*balance check*).
* **Ekspor ke Gambar**: Simpan visualisasi pohon Anda sebagai file gambar `.png` untuk laporan atau dokumentasi.

---

## Teknologi & Kebutuhan Sistem

* **Bahasa Pemrograman**: **Java**
* **Framework UI**: **Java Swing**
* **Pola Desain**: **MVC (Model-View-Controller)**
* **Versi Java**: **JDK 11** atau yang lebih baru
* **Sistem Operasi**: **Windows, macOS, atau Linux**

---

## 🚀 Instalasi & Menjalankan

Pastikan Anda telah menginstal **Java Development Kit (JDK)** di sistem Anda.

1.  **Clone Repositori**
    ```bash
    git clone [URL_REPOSITORI_ANDA]
    ```

2.  **Masuk ke Direktori Proyek**
    ```bash
    cd BinaryTreeApp
    ```

3.  **Kompilasi Kode Sumber**
    Perintah ini akan mengkompilasi semua file `.java` dan menempatkan hasilnya di dalam direktori `bin`.
    ```bash
    javac -d bin src/**/*.java
    ```

4.  **Jalankan Aplikasi**
    Gunakan perintah berikut untuk memulai aplikasi.
    ```bash
    java -cp bin gui.TreeGUI
    ```

---

## 📖 Panduan Penggunaan

### Operasi Dasar
1.  **Pilih Tipe Data** di panel kiri (misal: `Int (32)`).
2.  **Masukkan Nilai** di kolom input atas (misal: `50`).
3.  **Klik Tombol Aksi** seperti `Insert`, `Delete`, atau `Search`.
4.  Lihat hasilnya pada panel visualisasi dan area output di bagian bawah.

### Update Node
Untuk mengganti nilai sebuah node, gunakan format `nilaiLama,nilaiBaru` di kolom input, lalu klik `Update`.
* **Contoh**: `50,55` akan mengubah node `50` menjadi `55`.

### Traversal
Cukup klik tombol `Inorder`, `Preorder`, `Postorder`, atau `LevelOrder` untuk menampilkan urutan traversal di area output.

---

## 📂 Struktur Kode

Aplikasi ini dirancang dengan prinsip **Separation of Concerns** menggunakan pola **MVC**.

> ```
> src/
> ├── tree/         # Model: Logika data (BinaryTree.java, Node.java)
> └── gui/          # View & Controller: Antarmuka dan event (TreeGUI.java, TreePanel.java)
> ```



## Struktur Program

| File               | Deskripsi                                            |
|--------------------|------------------------------------------------------|
| `Node.java`        | Struktur dasar node pohon (data, kiri, kanan)        |
| `BinaryTree.java`  | Logika utama pohon biner: tambah, hapus, traversal   |
| `TreePanel.java`   | Panel khusus untuk menggambar visualisasi pohon      |
| `TreeGUI.java`     | Antarmuka utama pengguna dan penghubung ke logika    |

---

## Cara Kerja

### 1. Logika Pohon Biner (`BinaryTree.java`)

Menggunakan prinsip **Binary Search Tree (BST)**:
- **Tambah node** ke kiri jika lebih kecil, ke kanan jika lebih besar.
- **Hapus node** dengan menangani 3 kondisi: tanpa anak, satu anak, dua anak.
- **Traversal** (penelusuran) dilakukan secara rekursif:
  - Inorder (Kiri → Akar → Kanan)
  - Preorder (Akar → Kiri → Kanan)
  - Postorder (Kiri → Kanan → Akar)

### 2. Representasi Node (`Node.java`)

Setiap simpul memiliki:
- Tipe data `Comparable` agar bisa dibandingkan
- Referensi ke anak kiri dan kanan

### 3. Visualisasi Grafis (`TreePanel.java`)

Komponen `JPanel` khusus untuk:
- Menggambar pohon secara rekursif dari akar
- Mengatur posisi node secara proporsional
- Menggunakan `Graphics2D` untuk menggambar garis dan lingkaran node

### 4. Antarmuka Pengguna (`TreeGUI.java`)

Dibuat dengan komponen `Swing`, terdiri dari:
- `JTextField` untuk input nilai node
- `JButton` untuk aksi (tambah, hapus, clear)
- `JComboBox` untuk memilih metode traversal
- Panel visual untuk menampilkan pohon
- Event listener yang merespons input pengguna dan memanggil `repaint()`

---
