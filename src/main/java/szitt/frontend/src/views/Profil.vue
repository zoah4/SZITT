<template>
  <div class="max-w-3xl mx-auto p-4">
    <h1 class="text-2xl font-bold mb-4">Moj Profil</h1>
    <hr class="my-6" />
    <Notifications :userId="user.id" />

    <section class="border p-4 rounded">
      <h2 class="text-xl font-semibold mb-3">Podaci o instrukcijama</h2>
      <form @submit.prevent="updateInstructions">
        <div class="mb-2">
          <label>Email:</label>
          <input v-model="profile.email" class="input" disabled />
        </div>
        <div class="mb-2">
          <label>Ime:</label>
          <input v-model="profile.firstname" class="input" />
        </div>
        <div class="mb-2">
          <label>Prezime:</label>
          <input v-model="profile.lastname" class="input" />
        </div>

        <div class="mb-2">
          <label>Opis profila:</label>
          <textarea v-model="instrukcijePodaci.description" class="input" rows="4" placeholder="Unesite opis instruktora"></textarea>
        </div>
        <div class="mb-2">
          <label>Cijena po satu (EUR):</label>
          <input
              type="number"
              v-model.number="instrukcijePodaci.pricePerHour"
              class="input"
              min="0"
              step="0.01"
              placeholder="Unesite cijenu po satu"
          />
        </div>
        <button class="btn btn-primary" type="submit">Spremi podatke instrukcija</button>
      </form>
    </section>
    <div class="mb-4">
      <h3 class="text-lg font-semibold mb-2">Predmeti koje podučavam:</h3>
      <div v-for="predmet in allSubjects" :key="predmet.id" class="mb-1">
        <label>
          <input
              type="checkbox"
              :value="predmet.id"
              :checked="selectedSubjects.includes(predmet.id)"
              @change="toggleSubject(predmet.id, $event.target.checked)"
          />
          {{ predmet.name }}
        </label>
      </div>
    </div>
    <div class="mt-6">
      <h2 class="text-xl font-bold mb-3">Zahtjevi za instrukcije</h2>

      <div v-if="loading">Učitavanje zahtjeva...</div>
      <div v-else-if="reservations.length === 0" class="text-gray-500">Nema novih zahtjeva.</div>

      <div v-else class="space-y-4">
        <div
            v-for="rez in reservations"
            :key="rez.id"
            class="p-4 border rounded shadow bg-white">
          <div class="font-semibold">{{ rez.attendant.user.firstname }} {{ rez.attendant.lastname }}</div>
          <div class="text-sm text-gray-600">Predmet: {{ rez.subject.name }}</div>
          <div class="text-sm text-gray-600">Od: {{formatDate(rez.dateFrom) }} - Do: {{ formatDate(rez.dateTo) }}</div>
          <div class="text-sm text-gray-600">Trajanje: {{ rez.duration }}</div>

          <div class="text-sm text-gray-600">Lokacija: {{ rez.location }}</div>
          <div class="mt-3 space-x-2">
            <button
                class="bg-green-600 text-white px-3 py-1 rounded hover:bg-green-700"
                @click="prihvatiZahtjev(rez.id)">
              Prihvati
            </button>
            <button
                class="bg-red-600 text-white px-3 py-1 rounded hover:bg-red-700"
                @click="odbijZahtjev(rez.id)">
              Odbij
            </button>
          </div>
        </div>
      </div>
    </div>
    <ConfirmedReservations :userId="user.id" />
  </div>
</template>

<script>
import Notifications from '@/components/Notifications.vue'
import ConfirmedReservations from '@/components/ConfirmedReservationsInstructor.vue'
import { format } from 'date-fns'

export default {
  components: {
    Notifications,
    ConfirmedReservations
  },
  name: 'InstruktorProfile',
  data() {
    return {
      user : JSON.parse(localStorage.getItem('korisnik')),
      korisnik: '',
      profile: {
        email: '',
        password: '',
        firstname: '',
        lastname: '',
        id:''
      },
      instrukcijePodaci: {
        description: '',
        pricePerHour: null
      },
      selectedSubjects: [],
      allSubjects: [],
      reservations: [],
      loading: false,
    }
  },
  methods: {
    async getProfile() {
      try {
        const korisnik = this.user
        const korisnikEmail = korisnik?.email
        const res = await fetch( `/api/user/email/${korisnikEmail}`)
        const data1 = await res.json()
        console.log(data1)
        this.profile = data1
        const data2 = await fetch(`api/instructor/${this.profile.id}`)
        this.instrukcijePodaci = await data2.json()
        this.fetchReservations()
      } catch (err) {
        console.error('Greška pri dohvatu profila:', err)
      }
    },
    async fetchReservations() {
      this.loading = true
      try {
        const res = await fetch(`/api/instructor/${this.user.id}/reservations`, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`
          }
        })
        this.reservations = await res.json()
        console.log(this.reservations)

      } catch (err) {
        console.error('Greška pri dohvatu zahtjeva:', err)
      } finally {
        this.loading = false
      }
    },
    async prihvatiZahtjev(reservationId) {
      try {
        await fetch(`/api/instructor/${reservationId}/user/${this.user.id}/accept`, {
          method: 'PUT',
          headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`
          }
        })
        this.fetchReservations()
      } catch (err) {
        console.error('Greška pri prihvaćanju zahtjeva:', err)
      }
    },
    async odbijZahtjev(reservationId) {
      try {
        await fetch(`/api/instructor/${reservationId}/user/${this.user.id}/reject`, {
          method: 'PUT',
          headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`
          }
        })
        this.fetchReservations()
      } catch (err) {
        console.error('Greška pri odbijanju zahtjeva:', err)
      }
    },
    async getSubjects() {
      try {
        const res = await fetch('/api/subject/all')
        const data = await res.json()
        this.allSubjects = data
      } catch (err) {
        console.error('Greška pri dohvatu predmeta:', err)
      }
    },

    async updateInstructions() {
      const payload = {
        instructor: {
          email: this.profile.email,
          firstname: this.profile.firstname,
          lastname: this.profile.lastname,
          id: this.profile.id,
          password: this.profile.password
        },
        description: this.instrukcijePodaci.description,
        pricePerHour: this.instrukcijePodaci.pricePerHour
      }
      const res = await fetch(`/api/instructor/${this.profile.id}/edit`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${localStorage.getItem('token')}`
        },
        body: JSON.stringify(payload)
      })
      if (!res.ok) alert('Greška pri spremanju podataka instrukcija')
      else alert('Podaci instrukcija su spremljeni.')
    },

    async toggleSubject(subjectId, isChecked) {
      const instructorId = this.profile.id
      const url = `/api/instructor-subject/instructor/${instructorId}/subject/${subjectId}`
      const method = isChecked ? 'POST' : 'DELETE'

      try {
        const res = await fetch(url, {
          method,
          headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`
          }
        })

        if (!res.ok) throw new Error('Greška prilikom ažuriranja predmeta.')

        if (isChecked) {
          this.selectedSubjects.push(subjectId)
        } else {
          this.selectedSubjects = this.selectedSubjects.filter(id => id !== subjectId)
        }

      } catch (err) {
        console.error(err)
        alert('Nešto je pošlo po zlu.')
      }
    },
     formatDate(dateStr) {
        try {
          return format(new Date(dateStr), 'dd.MM.yyyy. HH:mm')
        } catch {
          return dateStr
      }
}
  },
  mounted() {
    this.getProfile()
    this.getSubjects()
  }
}
</script>

<style scoped>
.input {
  width: 100%;
  padding: 8px;
  border: 1px solid #ccc;
  border-radius: 8px;
}
.btn {
  padding: 8px 16px;
  border-radius: 8px;
  background-color: #2b6cb0;
  color: white;
}
</style>
