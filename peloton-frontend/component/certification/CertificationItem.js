import React, { useEffect } from "react";
import { Image, StyleSheet, Text, TouchableOpacity, View } from "react-native";

import { COLOR } from "../../utils/constants";

const CertificationItem = ({ item, currentTime }) => {
  const navigateToCertificate = (mission, certificationExample) => {
    console.log("이동");
  };

  const calculateLeftTime = () => {
    return new Date(item.mission.mission_duration.start_time - currentTime);
  };

  const timeForm = () => {
    const date = calculateLeftTime();
    return `${date.getHours()}시간 ${date.getMinutes()}분 ${date.getSeconds()}초`;
  };

  return (
    <TouchableOpacity
      activeOpacity={0.7}
      style={styles.item}
      onPress={() =>
        navigateToCertificate(item.mission, item.race.certification_example)
      }
    >
      <Image
        style={styles.itemImage}
        source={{ uri: item.race.thumbnail }}
        blurRadius={calculateLeftTime() < 0 ? 1 : 0}
      />
      <Text style={styles.itemTitle}>{item.race.title}</Text>
      <Text style={styles.itemSubtitle}>{timeForm()}</Text>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  item: {
    flex: 1,
    minWidth: 320,
    width: "100%",
    aspectRatio: 32 / 21,
    borderRadius: 10,
    overflow: "hidden",
    marginBottom: 20,
    backgroundColor: "black",
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

export default CertificationItem;
