import { ref, computed } from "vue";
import { defineStore } from "pinia";

export const useApartmentStore = defineStore("apartment", {
  state: () => ({
    apartment: {
      name: "",
      price: 0,
      address: "",
      latitude: 0,
      longitude: 0,
    },
  }),
  actions: {
    setApartmentInfo(apartmentData) {
      this.apartment = apartmentData;
    },
  },
});
