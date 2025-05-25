<template>
  <div class="mb-6 border rounded p-4">
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



</template>
<script>

export default {
  name: 'profilPolaznika',
  data() {
    return {
      prikaziProfil: false,
      profil: {
        id: '',
        email: '',
        firstname: '',
        lastname: '',
        password:'',
      }

    }
  },
  mounted() {
    this.dohvatiProfil()
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
      this.$router.push('/instrukcije')

    },
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
