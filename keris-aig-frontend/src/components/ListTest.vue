<template>
  <v-card class="mx-auto" max-width="300" tile>
    <v-list shaped>
      <v-subheader>REPORTS</v-subheader>
      <v-list-item-group v-model="active" no-action color="primary">
        <v-list-item v-for="(item, i) in items" :value="i" :key="i" @click="test">
          <v-list-item-icon>
            <v-icon v-text="item.icon"></v-icon>
          </v-list-item-icon>
          <v-list-item-content>
            <v-list-item-title v-text="item.text"></v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list-item-group>
    </v-list>
  </v-card>
</template>
<script>
export default {
  data: () => ({
    active: false,
    items: [
      { text: "Real-Time", icon: "mdi-clock" },
      { text: "Audience", icon: "mdi-account" },
      { text: "Conversions", icon: "mdi-flag" }
    ]
  }),
  methods: {
    test() {
      console.log(this.active);
    }
  }
};
</script>