package repositories

import models.Cart

import javax.inject.{Inject, Singleton}
import scala.collection.mutable.ListBuffer

@Singleton
class CartRepository @Inject()() {

  private val carts = new ListBuffer[Cart]()

  def getAllCarts: List[Cart] = carts.toList

  def getCartById(id: Long): Option[Cart] = carts.find(p => p.id == id)

  def createCart(cart: Cart): Cart = {
    val newCart = cart.copy(id = carts.size + 1)
    carts += newCart
    newCart
  }

  def updateCart(id: Long, cart: Cart): Unit = {
    val index = carts.indexWhere(p => p.id == id)
    if (index >= 0) {
      carts(index) = cart.copy(id = id)
    }
  }

  def deleteCart(id: Long): Unit = {
    val index = carts.indexWhere(p => p.id == id)
    if (index >= 0) {
      carts.remove(index)
    }
  }
}
