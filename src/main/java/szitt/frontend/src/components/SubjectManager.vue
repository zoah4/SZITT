<template>
  <div>
    <button @click="showModal = true" class="bg-blue-500 text-white px-4 py-2 rounded">
      Uredi predmete
    </button>

    <div v-if="showModal" class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
      <div class="bg-white p-6 rounded shadow-md w-full max-w-3xl relative">
        <h2 class="text-xl font-bold mb-4">Uredi predmete</h2>

        <button @click="reset()" class="absolute top-2 right-3 text-gray-500 hover:text-black">
          ✕
        </button>

        <input
            v-model="search"
            type="text"
            placeholder="Pretraži predmete..."
            class="w-full border px-3 py-2 mb-4 rounded"
        />

        <!-- Tablica predmeta -->
        <div class="overflow-x-auto max-h-60 overflow-y-auto border rounded mb-4">
          <table class="min-w-full table-auto border-collapse">
            <thead class="bg-gray-100 sticky top-0 z-10">
            <tr>
              <th class="text-left px-4 py-2 border-b">ID</th>
              <th class="text-left px-4 py-2 border-b">Naziv predmeta</th>
              <th class="text-left px-4 py-2 border-b">Akcije</th>
            </tr>
            </thead>
            <tbody>
            <tr
                v-for="subj in filteredSubjects"
                :key="subj.id"
                class="hover:bg-gray-50"
            >
              <td class="px-4 py-2 border-b">{{ subj.id }}</td>
              <td class="px-4 py-2 border-b">{{ subj.name }}</td>
              <td class="px-4 py-2 border-b space-x-2">
                <button @click="startEdit(subj)" class="text-blue-500 hover:underline text-sm">Uredi</button>
                <button @click="deleteSubject(subj.id)" class="text-red-500 hover:underline text-sm">Obriši</button>
              </td>
            </tr>
            </tbody>
          </table>
        </div>

        <!-- Forma za dodavanje / uređivanje -->
        <form @submit.prevent="isEditing ? updateSubject() : createSubject()" class="mt-4">
          <h3 class="text-lg font-semibold mb-2">
            {{ isEditing ? 'Uredi predmet' : 'Dodaj novi predmet' }}
          </h3>
          <input
              v-model="newSubject"
              type="text"
              placeholder="Naziv predmeta"
              required
              class="w-full border px-3 py-2 mb-2 rounded"
          />
          <button type="submit" class="bg-green-500 text-white px-4 py-2 rounded">
            {{ isEditing ? 'Spremi izmjene' : 'Dodaj predmet' }}
          </button>
        </form>
      </div>
    </div>
  </div>
</template>


<script>
export default {
  name: 'SubjectManager',
  data() {
    return {
      showModal: false,
      subjects: [],
      newSubject: '',
      search: '',
      isEditing: false,
      editingId: null
    }
  },
  computed: {
    filteredSubjects() {
      return this.subjects.filter(s =>
          s.name.toLowerCase().includes(this.search.toLowerCase())
      )
    }
  },
  methods: {
    async fetchSubjects() {
      try {
        const res = await fetch('/api/subject/all')
        this.subjects = await res.json()
      } catch (e) {
        console.error('Greška pri dohvaćanju predmeta', e)
      }
    },
    async createSubject() {
      try {
        await fetch('/api/subject/', {
          method: 'POST',
          headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
          body: new URLSearchParams({ name: this.newSubject })
        })
        this.newSubject = ''
        this.fetchSubjects()
      } catch (e) {
        console.error('Greška pri dodavanju predmeta', e)
      }
    },
    async deleteSubject(id) {
      if (!confirm('Jeste li sigurni da želite obrisati predmet?')) return
      try {
        await fetch(`/api/subject/${id}`, { method: 'DELETE' })
        this.fetchSubjects()
      } catch (e) {
        console.error('Greška pri brisanju predmeta', e)
      }
    },
    startEdit(subject) {
      this.newSubject = subject.name
      this.isEditing = true
      this.editingId = subject.id
    },
    async reset() {
      this.showModal = false
      this.isEditing = false
      this.newSubject = ''
      this.search=''
      this.$router.push('/profil')

    },
    async updateSubject() {
      try {
        await fetch(`/api/subject/${this.editingId}`, {
          method: 'PUT',
          headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
          body: new URLSearchParams({ name: this.newSubject })
        })
        this.isEditing = false
        this.newSubject = ''
        this.editingId = null
        this.fetchSubjects()
      } catch (e) {
        console.error('Greška pri uređivanju predmeta', e)
      }
    }
  },
  mounted() {
    this.fetchSubjects()
  }
}
</script>

<style scoped>
/* Modal scroll fix */
body {
  overflow-y: auto;
}
</style>
