<template>
  <div class="max-w-3xl mx-auto p-4">
    <h1 class="text-2xl font-bold mb-4">Moj Profil</h1>
    <hr class="my-6" />
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
  </div>
</template>

<script>
export default {
  name: 'InstruktorProfile',
  data() {
    return {
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
    }
  },
  methods: {
    async getProfile() {
      try {
        const korisnik = JSON.parse(localStorage.getItem('korisnik'))
        const korisnikEmail = korisnik?.email
        const res = await fetch( `/api/user/email/${korisnikEmail}`)
        const data1 = await res.json()
        console.log(data1)
        this.profile = data1
        const data2 = await fetch(`api/instructor/${this.profile.id}`)
        this.instrukcijePodaci = await data2.json()

      } catch (err) {
        console.error('Greška pri dohvatu profila:', err)
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
