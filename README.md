# bookstore-springboot-fs

Bookstore application made using Spring Boot and Angular, featuring ability for users to purchase, review, rate and exchange books.

![demo-2](https://user-images.githubusercontent.com/101462549/181914393-f2c7e15c-ac99-470f-ae51-bd55a20115c1.gif)

## Demo
Live demo for this application can be found [here](https://example.com).

API documentation can be found [here](https://example.com).

Credentials:
| Username | Password |
| ------ | ------ |
| `user1`   | `pass`  |
| `user2`   | `pass`  |
| `user3`   | `pass`  |
| `admin1`   | `test123admin` |


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

## License
[MIT](https://choosealicense.com/licenses/mit/)
