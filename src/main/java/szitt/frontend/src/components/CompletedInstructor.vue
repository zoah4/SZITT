<template>
  <div class="p-4 bg-white rounded shadow-md mb-6">
    <h2 class="text-xl font-bold mb-4">Odradene instrukcije</h2>

    <div v-if="reservations.length === 0" class="text-gray-500">
      Nema odrađenih instrukcija.
    </div>

    <ul v-else>
      <li
          v-for="rez in reservations"
          :key="rez.id"
          class="mb-3 p-3 border border-gray-200 rounded"
      >
        <p><strong>Predmet:</strong> {{ rez.subject.name }}</p>
        <p><strong>Polaznik:</strong> {{ rez.attendant.firstname }} {{ rez.attendant.lastname }}</p>
        <p><strong>Vrijeme:</strong> {{ formatDate(rez.dateFrom) }} - {{ formatDate(rez.dateTo) }}</p>
        <p><strong>Trajanje:</strong> {{ rez.duration }} min</p>
        <p><strong>Lokacija:</strong> {{ rez.location }}</p>
        <p><strong>Review:</strong> {{ rez.review ? rez.review.comment : "Nije ocjenjeno"}}</p>
        <p><strong>Ocjena:</strong> {{ rez.review ? rez.review.rating : "Nije ocjenjeno"}}</p>

        <hr>

      </li>
    </ul>
  </div>

</template>

<script>
export default {
  props: {
    userId: Number
  },
  name: 'CompletedReservationsCard',
  data() {
    return {
      reservations: [],
      reviews: [],

      instructorId: null,
    };
  },
  methods: {
    async fetchCompletedReservations() {
      try {
        const res = await fetch(`/api/instructor/${this.userId}/reservations/completed`);
        const data = await res.json();
        this.reservations = data;
        const res1 = await fetch(`/api/review/instructor/${this.userId}`);
        this.reviews = await res1.json();
        const reviewMap = new Map();
        this.reviews.forEach(review => {
          reviewMap.set(review.reservation.id, review);
        });
        this.reservations = this.reservations.map(res => {
          return {
            ...res,
            review: reviewMap.get(res.id) || null
          };
        });
        console.log(this.reservations)
      } catch (err) {
        console.error('Greška pri dohvaćanju rezervacija:', err);
      }
    },
    formatDate(dateString) {
      const options = {
        day: '2-digit',
        month: '2-digit',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
      };
      return new Date(dateString).toLocaleString('hr-HR', options);
    },
  },
  mounted() {

      this.fetchCompletedReservations();
  },
};
</script>

<style scoped>
/* Po želji dodatni stilovi */
</style>
