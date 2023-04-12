package repositories

import models.Category

import javax.inject.Inject
import scala.collection.mutable.ListBuffer

@Singleton
class CategoryRepository @Inject()() {

  private val categories = new ListBuffer[Category]()

  def getAllCategories: List[Category] = categories.toList

  def getCategoryById(id: Long): Option[Category] = categories.find(p => p.id == id)

  def getByNameOrCreateCategory(category: Category): Category =
    categories.find(p => p.name == category.name).getOrElse(createCategory(category))

  def createCategory(category: Category): Category = {
    val newCategory = category.copy(id = categories.size + 1)
    categories += newCategory
    newCategory
  }

  def updateCategory(id: Long, category: Category): Unit = {
    val index = categories.indexWhere(p => p.id == id)
    if (index >= 0) {
      categories(index) = category.copy(id = id)
    }
  }

  def deleteCategory(id: Long): Unit = {
    val index = categories.indexWhere(p => p.id == id)
    if (index >= 0) {
      categories.remove(index)
    }
  }


}
