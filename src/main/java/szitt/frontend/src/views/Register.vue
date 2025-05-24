<template>
  <div class="register">
    <h2>Registracija</h2>
    <form @submit.prevent="submitRegister">
      <input v-model="ime" placeholder="Ime" required />
      <input v-model="prezime" placeholder="Prezime" required />
      <input v-model="email" type="email" placeholder="Email" required />
      <input v-model="lozinka" type="password" placeholder="Lozinka" required />

      <select v-model="uloga" required>
        <option disabled value="">Odaberite ulogu</option>
        <option value="INSTRUKTOR">Instruktor</option>
        <option value="POLAZNIK">Polaznik</option>
      </select>
      <button type="submit">Registriraj se</button>
    </form>
    <p>Već imate račun? <router-link to="/login">Prijava</router-link></p>
  </div>
</template>

<script>
export default {
  name: 'UserRegister',
  data() {
    return {
      ime: '',
      prezime: '',
      email: '',
      lozinka: '',
      uloga: ''

    }
  },
  methods: {
    async submitRegister() {
      try {
        const response = await fetch('/api/auth/register', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            firstname: this.ime,
            lastname: this.prezime,
            email: this.email,
            password: this.lozinka,
            role: this.uloga

          })
        })

        if (!response.ok) throw new Error('Greška pri registraciji.')

        this.$router.push('/login')
      } catch (err) {
        alert(err.message)
      }
    }
  }
}
</script>
