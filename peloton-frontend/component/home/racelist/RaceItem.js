import React from "react";
import { StyleSheet, View } from "react-native";
import RaceItemImage from "./RaceItemImage";
import RaceItemText from "./RaceItemText";

const RaceItem = ({ item, parallaxProps }) => {
  return (
    <View style={styles.container}>
      <RaceItemImage item={item} parallaxProps={parallaxProps} />
      <RaceItemText item={item} />
    </View>
  );
};

export default RaceItem;

const styles = StyleSheet.create({
  container: {
    flex: 1,
    borderRadius: 20,
    backgroundColor: "white",
    marginVertical: 15,
    shadowColor: "#000",
    shadowOffset: {
      width: 2,
      height: 4,
    },
    shadowOpacity: 0.08,
    shadowRadius: 3.84,
    elevation: 5,
  },
});
