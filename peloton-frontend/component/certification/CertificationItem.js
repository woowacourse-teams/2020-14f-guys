import React, { useEffect, useState } from "react";
import { Image, StyleSheet, Text, TouchableOpacity, View } from "react-native";
import { useNavigation } from "@react-navigation/core";

import { CERTIFICATION_TYPE, COLOR } from "../../utils/constants";

const CertificationItem = ({ item, index, currentTime }) => {
  const [leftTime, setLeftTime] = useState(
    new Date(item.mission.mission_duration.start_time - currentTime),
  );
  const [certificationType, setCertificationType] = useState(
    leftTime > 0 ? CERTIFICATION_TYPE.WAIT : CERTIFICATION_TYPE.AVAILABLE,
  );
  const navigation = useNavigation();

  useEffect(() => {
    const startLeftTime =
      item.mission.mission_duration.start_time - currentTime;
    // TODO: 타임존 해결
    // new Date("2020-08-13T19:46:54.725+09:00") - currentTime) / 1000 / 3600
    // new Date("2020-08-13T19:46:54.725") - currentTime) / 1000 / 3600
    if (startLeftTime > 0) {
      setLeftTime(startLeftTime);
      setCertificationType(CERTIFICATION_TYPE.WAIT);
    } else {
      setLeftTime(item.mission.mission_duration.end_time - currentTime);
      setCertificationType(CERTIFICATION_TYPE.AVAILABLE);
    }
  }, [currentTime]);

  const navigateToCertificate = () => {
    navigation.navigate("CertificationSubmit", { index });
  };

  const timeForm = () => {
    // eslint-disable-next-line prettier/prettier
    return `${Math.floor(leftTime / 1000 / 3600 % 60)}시간 ${Math.floor(leftTime / 1000 / 60 % 60)}분 ${Math.floor(leftTime / 1000 % 60)}초`;
  };

  const onSelect = () => {
    if (certificationType === CERTIFICATION_TYPE.AVAILABLE) {
      return navigateToCertificate();
    } else if (certificationType === CERTIFICATION_TYPE.WAIT) {
      return () => {};
    }
  };

  if (leftTime < 0) {
    return null;
  }

  return (
    <TouchableOpacity
      activeOpacity={certificationType.activeOpacity}
      style={styles.item}
      onPress={onSelect}
    >
      <Image
        style={styles.itemImage}
        source={
          item.race.thumbnail
            ? { uri: item.race.thumbnail }
            : require("../../assets/default-race-thumbnail.jpg")
        }
        blurRadius={certificationType.blurRadius}
      />
      <View
        style={{
          ...styles.certificationInfo,
          backgroundColor: certificationType.color,
        }}
      />
      <Text style={styles.itemTitle}>{item.race.title}</Text>
      <Text style={styles.itemSubtitle}>{timeForm()}</Text>
      <View
        style={{
          ...styles.certificationTypeView,
          backgroundColor: certificationType.color,
        }}
      >
        <Text style={styles.certificationTypeMessage}>
          {certificationType.message}
        </Text>
      </View>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  item: {
    flex: 1,
    minWidth: 320,
    aspectRatio: 32 / 21,
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
  certificationInfo: {
    backgroundColor: COLOR.DARK_GRAY6,
    position: "absolute",
    bottom: 25,
    left: 7,
    width: 120,
    height: 60,
    opacity: 0.3,
    borderRadius: 10,
    padding: 10,
  },
  certificationTypeView: {
    position: "absolute",
    bottom: 30,
    right: 19.5,
    width: 100,
    height: 30,
    borderRadius: 15,
    justifyContent: "center",
    alignItems: "center",
    opacity: 0.9,
  },
  certificationTypeMessage: {
    color: COLOR.WHITE,
    fontWeight: "bold",
  },
});

export default CertificationItem;
