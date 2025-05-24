<template>
  <div class="max-w-5xl mx-auto p-4">
    <h1 class="text-2xl font-bold mb-4">Instrukcije</h1>

    <!-- Gumb za prikaz korisničkih podataka -->
    <button class="btn btn-secondary mb-4" @click="prikaziProfil = !prikaziProfil">
      {{ prikaziProfil ? 'Sakrij' : 'Uredi moje podatke' }}
    </button>

    <!-- Sekcija s korisničkim podacima -->
    <div v-if="prikaziProfil" class="mb-6 border rounded p-4">
      <h2 class="text-xl font-semibold mb-2">Moji podaci</h2>
      <form @submit.prevent="spremiProfil">
        <div class="mb-2">
          <label>Email:</label>
          <input v-model="profil.email" class="input" disabled />
        </div>
        <div class="mb-2">
          <label>Ime:</label>
          <input v-model="profil.firstname" class="input" />
        </div>
        <div class="mb-2">
          <label>Prezime:</label>
          <input v-model="profil.lastname" class="input" />
        </div>
        <button class="btn btn-primary mt-2" type="submit">Spremi</button>
      </form>
    </div>

    <!-- Pretraga instruktora -->
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

    <!-- Lista pronađenih instruktora -->
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
    </div>

    <!-- Modal za pregled instruktora -->
    <div v-if="odabraniInstruktor" class="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
      <div class="bg-white p-6 rounded w-full max-w-lg">
        <h2 class="text-xl font-bold mb-2">Profil instruktora</h2>
        <p><strong>Ime:</strong> {{ odabraniInstruktor.first_name }}</p>
        <p><strong>Prezime:</strong> {{ odabraniInstruktor.last_name }}</p>
        <p><strong>Email:</strong> {{ odabraniInstruktor.email }}</p>
        <button class="btn btn-secondary mt-4" @click="odabraniInstruktor = null">Zatvori</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'odabirInstrukcija',
  data() {
    return {
      prikaziProfil: false,
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
      odabraniInstruktor: null
    }
  },
  mounted() {
    this.dohvatiProfil()
    this.dohvatiPredmete()
  },
  methods: {
    async dohvatiProfil() {
      const korisnik = JSON.parse(localStorage.getItem('korisnik'))
      const res = await fetch(`/api/user/${korisnik.id}`)
      const data = await res.json()
      this.profil = data
      console.log(this.profil)
    },
    async spremiProfil() {
      const payload = {
        attendant: {
          email: this.profil.email,
          firstname: this.profil.firstname,
          lastname: this.profil.lastname,
          id: this.profil.id,
          password: this.profil.password
        },
        numOfReservation: 0
      }
      await fetch(`/api/attendant/${this.profil.id}/edit`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${localStorage.getItem('token')}`
        },
        body: JSON.stringify(payload)
      })
      alert('Profil ažuriran.')
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
