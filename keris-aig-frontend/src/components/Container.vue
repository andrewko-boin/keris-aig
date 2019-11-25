<template>
  <v-container class="grey lighten-5">
    <!-- Stack the columns on mobile by making one full-width and the other half-width -->
    <v-row style="border:1px solid">
      <v-col lg="12">
        <Table />
      </v-col>
    </v-row>
  </v-container>
</template>
<script>
import Table from "./components/Table";

export default {
  name: "Container",

  components: {
    Table
  },

  data: () => ({
    //
  })
};
</script>
