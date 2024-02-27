package com.example.taller1

class FavoritesStore {
    companion object {
        private val favorites : ArrayList<Destination> = ArrayList();

        fun addFavorite(newDestination: Destination) : Boolean {
            if (!favorites.contains(newDestination)) {
                favorites.add(newDestination);
                return true
            }
            return false
        }

        fun getFavorites() : ArrayList<Destination> {
            return ArrayList(favorites);
        }

        fun deleteFavorite(id: Int) : Boolean {
            return favorites.remove( favorites.find { dest -> dest.id == id } )
        }

        fun searchFavorite(id: Int) : Boolean {
            return favorites.find { dest -> dest.id == id } != null
        }

        fun getMostFrequentCategory() : ArrayList<Destination> {
            val freq: HashMap<String, Int> = HashMap()
            val favPerCategory : ArrayList<Destination> = ArrayList()

            if (favorites.size == 0) return favPerCategory

            for (dest in favorites) {
                if (freq[dest.categoria] == null) freq[dest.categoria] = 1
                else freq[dest.categoria] = freq[dest.categoria]!! + 1
            }
            var mostFrequentKey : String = ""
            var mostFrequentValue : Int = 0
            for ((key, value) in freq){
                if (mostFrequentValue < value) {
                    mostFrequentValue = value
                    mostFrequentKey = key
                }
            }

            for (dest in favorites) {
                if (dest.categoria == mostFrequentKey) favPerCategory.add(dest)
            }

            return favPerCategory
        }
    }
}