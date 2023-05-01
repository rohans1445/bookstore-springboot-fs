# bookstore-springboot-fs

Bookstore application made using Spring Boot and Angular, featuring ability for users to purchase, review, rate and exchange books.

![demo800](https://user-images.githubusercontent.com/101462549/222181355-5d80cce6-fc98-4fc4-b1e7-7632b3193ba6.gif)

## Demo
Live demo for this application can be found [here](http://d18joqudyse9m9.cloudfront.net).

API documentation can be found [here](http://bsngapi947.ddns.net/swagger-ui.html).

Credentials:
| Username | Password |
| ------ | ------ |
| `user1`   | `pass`  |
| `user2`   | `pass`  |
| `user3`   | `pass`  |
| `admin1`   | `pass` |

## Steps to setup API

1. **Clone the repository**

`git clone https://github.com/rohans1445/bookstore-springboot-fs.git`

2. **Create MySQL Database**

`create database bookstore-springboot-api`
- Setup initial data by running SQL script from `src/main/resources/bs.sql`

3. **Provide env variables**

- Set the following environment variables:
  - MYSQL_HOST
  - MYSQL_PORT
  - MYSQL_PASSWORD
  - STRIPE_KEY
  - STRIPE_WEBHOOK_KEY
  - JWT_SECRET
  - UI_BASE_URL

4. **Start the app using maven**
- `mvn spring-boot:run`
- App will be running on http://localhost:8081

## Steps to setup UI
1. `cd` into `/ui` from command prompt
2. Run `ng serve`
3. App will be running on http://localhost:4200

##  Account Types and Abilities
A `user` can...

- View, Purchase and Add books to their cart.
- Create exchange requests for books which they desire, in exchange for a book they own.


An `admin` can...
- Manage books present in the application and perform various operations on them.
- Manage discounts.

## Exchanges

Users have the abilities to exchange books with other users.
A user can navigate to the `Exchanges` page to view the exchange board.

![exchanges-page](https://user-images.githubusercontent.com/101462549/181914003-923603ab-604a-4489-9b0c-eb11a28f3fd1.png)

Here, a user can either accept an existing `exchange request` (made by another user) or create their own `exchange request`.

![image](https://user-images.githubusercontent.com/101462549/180609612-72c070f2-bb9c-4211-b7bd-c3139713ea37.png)


`user` column contains the user which has opened the `exchange request`

`has` column contains the book which is owned by the user.

`wants` column contains the books which is desired by the user.

`eligible for exchange` column indicates if the current logged in user can exchange with the request user.



An exchange request can go through the following stages:
| Status | Description |
| ------ | ------ |
| `Open`   | When an `exchange request` is created and available for exchange |
| `Success`   | When the books in the `exchange request` have been successfully exchanged between two users |
| `Closed`   | When an `exchange request` has been closed by the request opener |

***


## Libraries used
- Bootstrap 5
