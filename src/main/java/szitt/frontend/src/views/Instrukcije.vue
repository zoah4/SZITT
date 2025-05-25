<template>
  <div class="max-w-5xl mx-auto p-4">
    <h1 class="text-2xl font-bold mb-4">Instrukcije</h1>
    <ConfirmedReservations :userId="user.id" />

    <button class="btn btn-secondary mb-4" @click="prikaziProfil()">Prikaži profil</button>
    <Notifications :userId="user.id" />

    <br>

    <div class="mb-4 p-4 bg-gray-100 rounded shadow">
      <h3 class="text-lg font-semibold mb-2">Detalji instrukcije</h3>

      <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
        <div>
          <label class="block mb-1 font-medium">Datum od:</label>
          <input
              type="datetime-local"
              v-model="startTime"
              class="w-full border px-3 py-2 rounded"
          />
        </div>

        <div>
          <label class="block mb-1 font-medium">Datum do:</label>
          <input
              type="datetime-local"
              v-model="endTime"
              class="w-full border px-3 py-2 rounded"
          />
        </div>

        <div>
          <label class="block mb-1 font-medium">Trajanje(u minutama):</label>
          <input
              type="number" step="1"
              v-model="duration"
              class="w-full border px-3 py-2 rounded"
          />
        </div>

        <div>
          <label class="block mb-1 font-medium">Lokacija:</label>
          <select
              v-model="location"
              class="w-full border px-3 py-2 rounded">
            <option disabled value="">Odaberi lokaciju</option>
            <option v-for="loc in locations" :key="loc" :value="loc">
              {{ loc }}
            </option>
          </select>
        </div>
      </div>
    </div>

    <div class="mb-6">
      <h2 class="text-xl font-semibold mb-2">Pretraži instruktore po predmetima</h2>
      <select v-model="odabraniPredmet" class="input mb-2">
        <option value="">-- Odaberi predmet --</option>
        <option v-for="predmet in predmeti" :key="predmet.id" :value="predmet.id">
          {{ predmet.name }}
        </option>
      </select>
      <button @click="pretraziInstruktore" class="btn btn-secondary ml-2">Pretraži</button>
    </div>

    <div v-if="instruktori.length > 0">
      <h3 class="text-lg font-bold mb-2">Rezultati:</h3>
      <ul>
        <li v-for="instr in instruktori" :key="instr.id" class="mb-2 border p-3 rounded">
          <div class="font-semibold">{{ instr.instructor.user.firstname }} {{ instr.instructor.user.lastname }}</div>
          <div class="text-sm text-gray-500">{{ instr.instructor.user.email }}</div>
          <div class="text-sm text-gray-500">{{ instr.instructor.description }}</div>
          <button class="btn btn-sm btn-outline mt-2" @click="otvoriInstruktora(instr)">
            Pregledaj profil
          </button>
        </li>
      </ul>
      <div v-if="odabraniInstruktor && showInstructorProfile" class="mt-4 border p-4 rounded bg-gray-100">
        <h3 class="text-xl font-bold mb-2">
          {{ odabraniInstruktor.instructor.user.firstname }} {{ odabraniInstruktor.instructor.user.lastname }}
        </h3>
        <p class="text-sm text-gray-600 mb-1"><strong>Email:</strong> {{ odabraniInstruktor.instructor.user.email }}</p>
        <p class="mb-2"><strong>Opis:</strong> {{ odabraniInstruktor.instructor.description }}</p>

        <div class="mb-2">
          <strong>Ocjena:</strong>
          <span v-if="odabraniInstruktor.instructor.rating">
      {{ odabraniInstruktor.instructor.rating }}/5
    </span>
          <span v-else>
      Nema još ocjena
    </span>
        </div>

        <button class="btn btn-primary" @click="posaljiZahtjev">
          Pošalji zahtjev za instrukcije
        </button>

        <button class="btn btn-secondary ml-2" @click="showInstructorProfile = false">
          Zatvori
        </button>
      </div>
    </div>



  </div>
</template>

<script>
import Notifications from '@/components/Notifications.vue'
import ConfirmedReservations from '@/components/ConfirmedReservations.vue'

export default {
  components: {
    Notifications,
    ConfirmedReservations
  },
  name: 'odabirInstrukcija',
  data() {
    return {
      user: JSON.parse(localStorage.getItem('korisnik')),
      profil: {
        id: '',
        email: '',
        firstname: '',
        lastname: '',
        password:'',
      },
      predmeti: [],
      odabraniPredmet: '',
      instruktori: [],
      odabraniInstruktor: null,
      showInstructorProfile: false,
      startTime: '',
      endTime: '',
      duration: '',
      location: '',
      locations: ['ONLINE', 'KOD_POLAZNIKA', 'KOD_POLAZNIKA', 'NEGDJE_IZMEDU']
    }
  },
  mounted() {
    this.dohvatiPredmete()
  },
  methods: {
    async prikaziProfil() {
      this.$router.push('/profilPolaznik')

    },
    async dohvatiPredmete() {
      const res = await fetch('/api/subject/all')
      const data = await res.json()
      this.predmeti = data
    },
    async pretraziInstruktore() {
      if (!this.odabraniPredmet) return
      const res = await fetch(`/api/instructor-subject/subject/${this.odabraniPredmet}`, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`
        }
      })
      const data = await res.json()
      this.instruktori = data
      console.log(this.instruktori)
    },
    otvoriInstruktora(instr) {
      this.odabraniInstruktor = instr
      console.log(instr.instructor.id)
      this.showInstructorProfile = true
    },
    async posaljiZahtjev() {
      const korisnik = JSON.parse(localStorage.getItem('korisnik'))

      const payload = {
          attendantId: korisnik.id,
          instructorId: this.odabraniInstruktor.instructor.id,
          subjectId: this.odabraniPredmet,
          dateFrom: this.startTime,
          dateTo: this.endTime,
          duration: this.duration,
          location: this.location,
          status:"NA_CEKANJU",
          created: this.startTime
      }
      console.log(payload)
      const res = await fetch(`/api/attendant/${korisnik.id}/instructor/${this.odabraniInstruktor.instructor.id}/reservation`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${localStorage.getItem('token')}`
        },
        body: JSON.stringify(payload)
      })

      if (res.ok) {
        alert('Zahtjev je poslan instruktoru!')
        this.$router.push('/instrukcije')

      } else {
        alert('Greška pri slanju zahtjeva.')
      }
    }
  }
}
</script>

<style scoped>
.input {
  width: 100%;
  padding: 8px;
  border-radius: 6px;
  border: 1px solid #ccc;
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
