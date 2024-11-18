// stores/mapStore.js
import { defineStore } from "pinia";

export const useMapStore = defineStore("mapStore", {
  state: () => ({
    aptName: null,
    dealAmount: null,
    address: null,
    markerPosition: { lat: null, lng: null }, // { lat: 37.5665, lng: 126.9780 } 형태
  }),
  actions: {
    setAptDetails(name, amount, address, position) {
      this.aptName = name;
      this.dealAmount = amount;
      this.address = address;
      this.markerPosition = position;
    },
  },
});
