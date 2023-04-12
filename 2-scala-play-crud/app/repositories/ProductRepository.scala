package repositories

import scala.collection.mutable.ListBuffer
import models.Product

import javax.inject.Singleton
import javax.inject.Inject

@Singleton
class ProductRepository @Inject()(categoryRepository: CategoryRepository) {

  private val products = new ListBuffer[Product]()

  def getAllProducts: List[Product] = products.toList

  def getProductById(id: Long): Option[Product] = products.find(p => p.id == id)

  def createProduct(product: Product): Product = {
    val newProduct = product.copy(id = products.size + 1, category = categoryRepository.getByNameOrCreateCategory(product.category))
    products += newProduct
    newProduct
  }

  def updateProduct(id: Long, product: Product): Unit = {
    val index = products.indexWhere(p => p.id == id)
    if (index >= 0) {
      products(index) = product.copy(id = id, category = categoryRepository.getByNameOrCreateCategory(product.category))
    }
  }

  def deleteProduct(id: Long): Unit = {
    val index = products.indexWhere(p => p.id == id)
    if (index >= 0) {
      products.remove(index)
    }
  }

}
