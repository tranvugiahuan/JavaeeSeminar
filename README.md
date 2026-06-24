# Seminar - Spring Boot Transaction Management Demo

Ung dung demo quan ly don hang don gian cho de tai Transaction Management trong Spring Boot.

## Chuc nang

- Tao don hang moi tu danh sach san pham co san.
- Chon san pham vao gio hang va chinh so luong truoc khi thanh toan.
- Tinh tong tien don hang tren giao dien va trong Service.
- Nhap so luong vuot ton kho de kiem tra rollback bang `@Transactional`.
- Hien thi danh sach don hang da tao.
- San pham va so luong ton kho duoc luu trong bang `Products`.

## Cong nghe

- Java 17
- Spring Boot
- Spring MVC + Thymeleaf
- Spring Data JPA / Hibernate
- SQL Server
- Bootstrap 5

## Tao database

Mo SQL Server Management Studio hoac Azure Data Studio, chay file:

```sql
database.sql
```

Mac dinh ung dung ket noi toi:

```text
jdbc:sqlserver://localhost:1433;databaseName=OrderDemoDb;encrypt=true;trustServerCertificate=true
username: sa
password: YourStrong@Passw0rd
```

File `database.sql` se tao cac bang `Products`, `Orders`, `OrderDetails` va them san pham mau.

Co the doi thong tin ket noi bang bien moi truong:

```powershell
$env:DB_URL="jdbc:sqlserver://localhost:1433;databaseName=OrderDemoDb;encrypt=true;trustServerCertificate=true"
$env:DB_USERNAME="sa"
$env:DB_PASSWORD="your-password"
```

## Chay ung dung

```powershell
$env:JAVA_HOME="C:\Program Files\Java\jdk-17"
$env:Path="$env:JAVA_HOME\bin;$env:Path"
.\mvnw.cmd spring-boot:run
```

Sau do mo:

```text
http://localhost:8080/orders
```

Neu chua cau hinh duoc SQL Server, co the chay tam profile demo bang H2 in-memory de xem giao dien:

```powershell
.\mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=demo"
```

## Kiem tra rollback

1. Nhap ten khach hang va chon it nhat mot san pham.
2. Trong gio hang, sua so luong cua mot san pham lon hon so luong ton kho dang hien thi.
3. Bam `Thanh toan`.
4. Ung dung bao loi vuot ton kho sau khi `saveAndFlush`, transaction rollback nen don hang khong xuat hien trong danh sach.

Phan xu ly transaction nam trong:

```text
src/main/java/com/example/seminar/order/service/OrderService.java
```
