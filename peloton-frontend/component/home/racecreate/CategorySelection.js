import React from "react";
import { Dimensions, FlatList, StyleSheet, Text, View } from "react-native";
import { CATEGORY } from "../../../utils/constants";
import CategoryItem from "./CategoryItem";

const CategorySelection = () => {
  return (
    <View style={styles.container}>
      <Text style={styles.title}>어떤 레이스를 원하세요?</Text>
      <FlatList
        data={CATEGORY}
        renderItem={CategoryItem}
        keyExtractor={(item) => item.value}
        showsVerticalScrollIndicator={false}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    paddingHorizontal: Dimensions.get("window").width * 0.075,
  },
  title: {
    marginTop: 30,
    marginBottom: 22,
    fontSize: 18,
    fontWeight: "600",
  },
});

export default CategorySelection;
