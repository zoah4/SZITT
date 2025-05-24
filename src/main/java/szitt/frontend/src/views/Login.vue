<template>
  <div class="login">
    <h2>Login</h2>
    <form @submit.prevent="submitLogin">
      <input v-model="email" type="email" placeholder="Email" required />
      <input v-model="password" type="password" placeholder="Lozinka" required />
      <button type="submit">Prijavi se</button>
    </form>
    <p>Nemate račun? <router-link to="/register">Registriraj se</router-link></p>
  </div>
</template>

<script>
export default {
  name: 'UserLogin',
  data() {
    return {
      email: '',
      password: ''
    }
  },
  methods: {
    async submitLogin() {
      try {
        const response = await fetch('/api/auth/login', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            email: this.email,
            password: this.password
          })
        })

        if (!response.ok) throw new Error('Greška pri prijavi.')
        const dataRes = await response.json()
        console.log(dataRes)
        localStorage.setItem('korisnik', JSON.stringify(dataRes))

        if (dataRes.uloga === 'INSTRUKTOR') {
          this.$router.push('/profil');
        } else if (dataRes.uloga === 'POLAZNIK') {
          this.$router.push('/instrukcije');
        } else {
          this.$router.push('/login')

        }
      } catch (err) {
        alert(err.message)
      }
    }
  }
}
</script>
