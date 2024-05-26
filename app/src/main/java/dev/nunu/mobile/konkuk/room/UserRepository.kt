package dev.nunu.mobile.konkuk.room

interface UserRepository {
    suspend fun getAll(): List<User>
    fun loadAllByIds(userIds: IntArray): List<User>
    fun findByName(first: String, last: String): User
    fun insertAll(vararg users: User)
    fun delete(user: User)
}

class DefaultUserRepository(private val userDao: UserDao) : UserRepository {
    override suspend fun getAll(): List<User> = userDao.getAll()
    override fun loadAllByIds(userIds: IntArray): List<User> = userDao.loadAllByIds(userIds)
    override fun findByName(first: String, last: String): User = userDao.findByName(first, last)
    override fun insertAll(vararg users: User) = userDao.insertAll(*users)
    override fun delete(user: User) = userDao.delete(user)
}