package com.pedrocarrillo

import java.util.*

/**
 * Created by pedrocarrillo on 8/18/17.
 */

class Algorithm {

    val user1 = User("user1")
    val user2 = User("user2")
    val user3 = User("user3")
    val user4 = User("user4")

    val targetUser = User("target user")


    val item1 = Song("item1")
    val item2 = Song("item2")
    val item3 = Song("item3")

    val itemTarget = Song("itemTarget")

    val loveLimit = 8.0f
    val configurationCount = Array<Int>(8, {i -> 0})

    init {
        // votes of user 1
        voteForSong(user1, itemTarget, 5.0f)
        voteForSong(user1, item1, 8.0f)
        // votes of user 2
        voteForSong(user2, itemTarget, 9.0f)
        voteForSong(user2, item1, 9.0f)
        // votes of user 3
        voteForSong(user3, itemTarget, 2.0f)
        voteForSong(user3, item1, 3.0f)
        // votes of user 4
        voteForSong(user4, itemTarget, 9.0f)
        voteForSong(user4, item2, 8.0f)
        voteForSong(user4, item3, 2.0f)
        // votes of target user
        voteForSong(targetUser, item1, 3.0f)
        voteForSong(targetUser, item2, 4.0f)
        voteForSong(targetUser, item3, 9.0f)

        val users = HashSet<User>()
        users.add(targetUser);
        dfs(targetUser, itemTarget, users)


        println(Arrays.toString(configurationCount))
    }

    fun dfs(targetUser : User, targetItem : Item, users : HashSet<User>) {
        val itemQueue = LinkedList<Item>()
        for (song in targetUser.votedItems) {
            itemQueue.push(song)
        }
        while (!itemQueue.isEmpty()) {
            val song = itemQueue.pop()
            for (entry in song.getScores().entries) {
                if (targetItem.getScores().containsKey(entry.key) && targetUser != entry.key) {
                    var otherUser = entry.key
                    var down = song.getScores().get(targetUser)
                    var right = song.getScores().get(otherUser)
                    var up = itemTarget.songScores.get(otherUser)

                    if (down!! >= loveLimit) {
                        if (right!! >= loveLimit) {
                            if (up!! >= loveLimit) {
                                configurationCount[7]++
                            } else {
                                configurationCount[6]++
                            }
                        } else {
                            if (up!! >= loveLimit) {
                                configurationCount[5]++
                            } else {
                                configurationCount[4]++
                            }
                        }
                    } else {
                        if (right!! >= loveLimit) {
                            if (up!! >= loveLimit) {
                                configurationCount[3]++
                            } else {
                                configurationCount[2]++
                            }
                        } else {
                            if (up!! >= loveLimit) {
                                 configurationCount[1]++
                            } else {
                                configurationCount[0]++
                            }
                        }
                    }
                    users.add(entry.key)
                }
            }
        }
    }

    // function to vote for an item
    fun voteForSong(user : User, item : Item, score : Float) {
        item.vote(user, score)
        user.votedItems.add(item)
    }

    interface Item {
        fun vote(user : User, score : Float)

        fun getScores() : HashMap<User, Float>
    }

    class Song (val name : String) : Item {

        val songScores = HashMap<User, Float>()

        override fun vote(user : User, score : Float) {
            songScores.put(user, score)
        }

        override fun getScores(): HashMap<User, Float> {
            return songScores
        }

    }

    data class User (val name : String) {

        val votedItems = ArrayList<Item>()

    }
}

fun main(args: Array<String>) {
    val a = Algorithm()

}
