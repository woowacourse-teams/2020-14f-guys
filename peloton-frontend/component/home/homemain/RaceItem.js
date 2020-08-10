import React from "react";
import { StyleSheet, View } from "react-native";
import RaceItemImage from "./RaceItemImage";
import RaceItemText from "./RaceItemText";
import { COLOR } from "../../../utils/constants";

const RaceItem = ({ item, parallaxProps }) => {
  return (
    <View style={styles.container}>
      <RaceItemImage item={item} parallaxProps={parallaxProps} />
      <RaceItemText item={item} />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    borderRadius: 20,
    backgroundColor: COLOR.WHITE,
    marginVertical: 15,
    shadowColor: COLOR.BLACK,
    shadowOffset: {
      width: 2,
      height: 4,
    },
    shadowOpacity: 0.08,
    shadowRadius: 3.84,
    elevation: 5,
  },
});

export default RaceItem;
