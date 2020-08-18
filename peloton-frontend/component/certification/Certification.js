import React, { useCallback, useEffect, useState } from "react";
import { FlatList, StyleSheet, Text, View } from "react-native";
import { useRecoilState } from "recoil";
import { useFocusEffect } from "@react-navigation/core";

import {
  raceMissionFixture,
  raceMissionState,
} from "../../state/certification/RaceMissionState";
import CertificationItem from "./CertificationItem";
import { COLOR } from "../../utils/constants";

const Certification = () => {
  const [certifications, setCertifications] = useRecoilState(raceMissionState);
  const [currentTime, setCurrentTime] = useState(new Date());

  useEffect(() => {
    // TODO: 미션 정보 받아오는 부분이 여기 들어가야함
    setCertifications(raceMissionFixture);
  }, []);

  useFocusEffect(
    useCallback(() => {
      const intervalId = setInterval(() => setCurrentTime(new Date()), 1000);
      return () => {
        clearInterval(intervalId);
      };
    }, [])
  );

  return (
    <View style={styles.container}>
      {certifications.length > 0 ? (
        <FlatList
          data={certifications}
          renderItem={({ item, index }) => (
            <CertificationItem
              item={item}
              index={index}
              currentTime={currentTime}
            />
          )}
          keyExtractor={(item) => String.valueOf()(item.mission.id)}
          showsVerticalScrollIndicator={false}
        />
      ) : (
        <View style={styles.messageContainer}>
          <Text style={styles.message}>아직 참여중인 레이스가 없네요 😀</Text>
          <Text style={styles.message}>새로운 레이스에 참여해보세요</Text>
        </View>
      )}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  messageContainer: {
    alignItems: "center",
  },
  message: {
    fontSize: 20,
    fontWeight: "300",
    fontStyle: "normal",
    lineHeight: 35,
    color: COLOR.GRAY1,
  },
});

export default Certification;
