import React from "react";
import { Image, StyleSheet, Text, TouchableOpacity } from "react-native";

import { COLOR } from "../../../utils/constants";

const CategoryItem = ({ item, onSelect }) => {
  return (
    <TouchableOpacity
      activeOpacity={0.7}
      style={styles.item}
      onPress={() => onSelect(item.category)}
    >
      <Image style={styles.itemImage} source={{ uri: item.src }}/>
      <Text style={styles.itemTitle}>{item.title}</Text>
      <Text style={styles.itemSubtitle}>{item.subtitle}</Text>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  item: {
    minWidth: 320,
    width: "100%",
    aspectRatio: 32 / 21,
    borderRadius: 10,
    overflow: "hidden",
    marginBottom: 20,
  },
  itemImage: {
    flex: 1,
  },
  itemTitle: {
    position: "absolute",
    left: 19.5,
    bottom: 49,
    color: COLOR.WHITE,
    fontWeight: "bold",
    fontSize: 30,
  },
  itemSubtitle: {
    position: "absolute",
    left: 19.5,
    bottom: 30,
    color: COLOR.WHITE,
    fontSize: 15,
  },
});

export default CategoryItem;
