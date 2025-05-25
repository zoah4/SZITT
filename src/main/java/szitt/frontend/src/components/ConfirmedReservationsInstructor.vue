<template>
  <div class="p-4 bg-white rounded shadow-md">
    <h2 class="text-xl font-bold mb-4">Potvrđene rezervacije</h2>

    <div v-if="reservations.length === 0" class="text-gray-500">Nemate potvrđenih rezervacija.</div>

    <ul v-else class="space-y-3">
      <li v-for="rez in reservations" :key="rez.id" class="p-3 border rounded">
        <div><strong>Polaznik:</strong> {{ rez.attendant.user.firstname }} {{ rez.attendant.user.lastname }}</div>
        <div><strong>Predmet:</strong> {{ rez.subject.name }}</div>
        <div><strong>Vrijeme:</strong> {{ formatDate(rez.dateFrom) }} - {{ formatDate(rez.dateTo) }}</div>
        <div><strong>Trajanje:</strong> {{ rez.duration }}</div>
        <div><strong>Lokacija:</strong> {{ rez.location }}</div>
        <button
            class="mt-3 px-3 py-1 bg-red-500 hover:bg-red-600 text-white rounded text-sm"
            @click="otkaziRezervaciju(rez.id)"
        >
          Otkaži rezervaciju
        </button>
        <button
            class="mt-3 px-3 py-1 bg-red-500 hover:bg-red-600 text-white rounded text-sm"
            @click="gotovaRezervacija(rez.id)"
        >
          Rezervacija odrađena
        </button>
      </li>
    </ul>
  </div>
</template>

<script>
import { format } from 'date-fns'

export default {
  name: 'ConfirmedReservations',
  props: {
    userId: {
      type: Number,
      required: true
    }
  },
  data() {
    return {
      reservations: [],
    }
  },
  methods: {
    async fetchReservations() {
      try {
        const response = await fetch(`/api/instructor/${this.userId}/reservations/accepted`)
        const data = await response.json()

        this.reservations = data
        console.log(this.reservations)
      } catch (err) {
        console.error('Greška pri dohvaćanju rezervacija:', err)
      }
    },
    formatDate(dateStr) {
      try {
        return format(new Date(dateStr), 'dd.MM.yyyy. HH:mm')
      } catch {
        return dateStr
      }
    },
    async otkaziRezervaciju(reservationId) {
      if (!confirm('Jeste li sigurni da želite otkazati ovu rezervaciju?')) return

      try {
        const response = await fetch(`/api/reservation/${reservationId}/user/${this.userId}/cancel`, {
          method: 'PUT'
        })

        if (!response.ok) {
          throw new Error('Greška pri otkazivanju rezervacije.')
        }

        this.reservations = this.reservations.filter(r => r.id !== reservationId)
        alert('Rezervacija je otkazana.')
      } catch (err) {
        console.error('Greška:', err)
        alert('Neuspješno otkazivanje.')
      }
    },
    async gotovaRezervacija(reservationId) {
      if (!confirm('Jeste li sigurni da želite dovršiti ovu rezervaciju?')) return

      try {
        const response = await fetch(`/api/reservation/${reservationId}/user/${this.userId}/complete`, {
          method: 'PUT'
        })

        if (!response.ok) {
          throw new Error('Greška pri odrađivanju rezervacije.')
        }

        this.reservations = this.reservations.filter(r => r.id !== reservationId)
        alert('Instrukcije su održane.')
      } catch (err) {
        console.error('Greška:', err)
        alert('Neuspješno potvrđivanje.')
      }
    }

  },

  mounted() {
    this.fetchReservations()
  }
}
</script>

<style scoped>
</style>
