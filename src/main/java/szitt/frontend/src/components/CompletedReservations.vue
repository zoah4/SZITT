<template>
  <div class="p-6">
    <h2 class="text-2xl font-bold mb-4">Odradene rezervacije</h2>

    <!-- Filtri -->
    <div class="flex flex-wrap gap-4 mb-4">
      <select v-model="selectedInstructor" class="border px-3 py-2 rounded">
        <option value="">Svi instruktori</option>
        <option v-for="i in uniqueInstructors" :key="i" :value="i">{{ i.user.firstname }} {{i.user.lastname}}</option>
      </select>

      <select v-model="selectedSubject" class="border px-3 py-2 rounded">
        <option value="">Svi predmeti</option>
        <option v-for="s in uniqueSubjects" :key="s" :value="s">{{ s.name }}</option>
      </select>
    </div>

    <table class="min-w-full table-auto border-collapse">
      <thead class="bg-gray-100">
      <tr>
        <th class="text-left px-4 py-2 border">Instruktor</th>
        <th class="text-left px-4 py-2 border">Predmet</th>
        <th class="text-left px-4 py-2 border">Datum</th>
        <th class="text-left px-4 py-2 border">Trajanje</th>

        <th class="text-left px-4 py-2 border">Recenzije</th>
      </tr>
      </thead>
      <tbody>
      <tr
          v-for="rez in filteredReservations"
          :key="rez.id"
          class="border hover:bg-gray-50"
      >
        <td class="px-4 py-2 border">{{ rez.instructor.user.firstname }} {{ rez.instructor.user.lastname }}</td>
        <td class="px-4 py-2 border">{{ rez.subject.name }}</td>
        <td class="px-4 py-2 border">{{ formatDate(rez.dateFrom) }}</td>
        <td class="px-4 py-2 border">{{ rez.duration }} min</td>
        <td class="px-4 py-2 border space-x-2">
          <button
              v-if="!rez.review"
              @click="openReviewForm(rez)"
              class="text-blue-500 hover:underline text-sm"
          >Dodaj recenziju</button>

          <template v-else>
            <button @click="openReviewForm(rez, true)" class="text-yellow-600 hover:underline text-sm">Uredi</button>
            <button @click="deleteReview(rez)" class="text-red-500 hover:underline text-sm">Obriši</button>
          </template>
        </td>
      </tr>
      </tbody>
    </table>

    <div v-if="showReviewForm" class="mt-6 border p-4 rounded bg-gray-50">
      <h3 class="font-semibold mb-2">
        {{ isEditing ? 'Uredi recenziju' : 'Dodaj recenziju' }} za: {{ selectedReservation.subject.name }}
      </h3>
      <textarea v-model="reviewText" rows="3" class="w-full border rounded p-2 mb-2" placeholder="Unesi recenziju"></textarea>
      <select v-model="reviewRating" class="border rounded px-2 py-1 mb-2">
        <option disabled value="">Odaberi ocjenu</option>
        <option v-for="n in 5" :key="n" :value="n">{{ n }}</option>
      </select>
      <div>
        <button @click="submitReview" class="bg-green-600 text-white px-4 py-2 rounded">Spremi</button>
        <button @click="cancelReview" class="ml-2 text-gray-600 hover:underline">Odustani</button>
      </div>
    </div>
  </div>
</template>
<script>
import {format} from "date-fns";
import {hr} from "date-fns/locale";

export default {
  props: {
    userId: Number
  },
  data() {
    return {
      reservations: [],
      reviews: [],
      uniqueInstructors: [],
      uniqueSubjects: [],
      selectedInstructor: '',
      selectedSubject: '',
      showReviewForm: false,
      selectedReservation: null,
      reviewText: '',
      reviewRating: '',
      isEditing: false
    }
  },
  computed: {

    filteredReservations() {
      console.log(this.selectedInstructor)
      return this.reservations.filter(r =>
          (!this.selectedInstructor || r.instructor.id === this.selectedInstructor.id) &&
          (!this.selectedSubject || r.subject.id === this.selectedSubject.id)
      );
    }
  },
  methods: {
    async fetchReservations() {
      const res = await fetch(`/api/attendant/${this.userId}/reservations/completed`);
      this.reservations = await res.json();
      const res1 = await fetch(`/api/review/attendant/${this.userId}`);
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
    },

    async fetchInstructors() {
      try {
        const res = await fetch('/api/instructor/all');
        const data = await res.json();
        this.uniqueInstructors = data;
      } catch (err) {
        console.error('Greška pri dohvaćanju instruktora:', err);
      }
    },
    async fetchSubjects() {
      try {
        const res = await fetch('/api/subject/all');
        const data = await res.json();
        this.uniqueSubjects = data;
      } catch (err) {
        console.error('Greška pri dohvaćanju predmeta:', err);
      }
    },
    openReviewForm(reservation) {
      this.selectedReservation = reservation;
      this.showReviewForm = true;
      if (reservation.review) {
        this.isEditing = true;
        this.reviewText = reservation.review.comment;
        this.reviewRating = reservation.review.rating;
      } else {
        this.isEditing = false;
        this.reviewText = '';
        this.reviewRating = '';
      }
    },
    cancelReview() {
      this.showReviewForm = false;
      this.reviewText = '';
      this.reviewRating = '';
      this.selectedReservation = null;
    },
    async submitReview() {
      const reviewDto = {
        rating: this.reviewRating,
        comment: this.reviewText,
        date: new Date().toISOString()
      };
      if(this.isEditing) {
        await fetch(`/api/review/${this.selectedReservation.review.id}`, {
          method: 'PUT',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(reviewDto)
        });
      } else {
        await fetch(`/api/attendant/${this.userId}/reservation/${this.selectedReservation.id}/reviews`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify(reviewDto)
        });
      }

      this.cancelReview();
      await this.fetchReservations();
    },
    async deleteReview(reservation) {
      await fetch(`/api/review/${reservation.review.id}`, {
        method: 'DELETE'
      });
      this.isEditing = false;
      this.reviewText = '';
      this.reviewRating = '';
      await this.fetchReservations();
    },
    formatDate(dateStr) {
      try {
        return format(new Date(dateStr), 'd. MMM yyyy. HH:mm', { locale: hr })
      } catch {
        return dateStr
      }
    }
  },
  mounted() {
    this.fetchReservations();
    this.fetchSubjects();
    this.fetchInstructors();
  }
}
</script>
