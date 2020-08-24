import React from "react";
import { StyleSheet, View } from "react-native";
import Announcement from "./Announcement";

const Setting = () => {
  return (
    <View style={styles.container}>
      <Announcement />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
});

export default Setting;
