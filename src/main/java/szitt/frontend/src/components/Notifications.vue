<template>
  <div class="my-4">
    <button
        @click="toggle"
        class="btn btn-outline bg-blue-600 hover:bg-blue-700 text-white font-semibold px-4 py-2 rounded"
    >
      {{ prikazi ? 'Zatvori obavijesti' : 'Prikaži obavijesti' }}
    </button>

    <transition name="fade">
      <div v-if="prikazi" class="mt-4 p-4 bg-white border rounded shadow max-w-xl">
        <h2 class="text-lg font-bold mb-3">Obavijesti</h2>

        <div v-if="loading">Učitavanje...</div>
        <div v-else-if="notifications.length === 0" class="text-gray-500">Nema obavijesti.</div>

        <ul v-else class="space-y-2">
          <li
              v-for="notif in notifications"
              :key="notif.id"
              class="border p-3 rounded bg-gray-50"
          >
            <div class="font-semibold">{{ notif.content }}</div>
            <div class="text-sm">{{ notif.type }}</div>
            <div class="text-xs text-gray-400 mt-1">Zahtjev kreiran:
              {{ formatDate(notif.created) }}
            </div>
            <hr>
          </li>
        </ul>
      </div>
    </transition>
  </div>
</template>

<script>
import { format } from 'date-fns'
import { hr } from 'date-fns/locale'

export default {
  name: 'NotificationsComponent',
  props: {
    userId: {
      type: Number,
      required: true
    }
  },
  data() {
    return {
      notifications: [],
      loading: false,
      prikazi: false
    }
  },
  methods: {
    toggle() {
      this.prikazi = !this.prikazi
      if (this.prikazi && this.notifications.length === 0) {
        this.fetchNotifications()
      }
    },
    async fetchNotifications() {

      this.loading = true
      try {
        const res = await fetch(`/api/notification/user/${this.userId}`, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`
          }
        })
        const data = await res.json()
        this.notifications = data
      } catch (err) {
        console.error('Greška pri dohvatu obavijesti:', err)
      } finally {
        this.loading = false
        console.log(this.notifications)
      }
    },
    formatDate(dateStr) {
      try {
        return format(new Date(dateStr), 'd. MMM yyyy. HH:mm', { locale: hr })
      } catch {
        return dateStr
      }
    }
  }
}
</script>

<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: all 0.3s ease;
}
.fade-enter-from,
.fade-leave-to {
  opacity: 0;
  transform: scale(0.98);
}
.btn {
  padding: 6px 12px;
  background-color: #2b6cb0;
  color: white;
  border-radius: 6px;
}
.btn-outline {
  background: transparent;
  border: 1px solid #2b6cb0;
  color: #2b6cb0;
}
</style>
