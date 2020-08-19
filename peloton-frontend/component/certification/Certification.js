import React, { useCallback, useEffect, useState } from "react";
import { FlatList, StyleSheet, Text, View } from "react-native";
import { useRecoilState, useRecoilValue } from "recoil";
import { useFocusEffect } from "@react-navigation/core";

import { raceMissionState } from "../../state/certification/RaceMissionState";
import CertificationItem from "./CertificationItem";
import { COLOR } from "../../utils/constants";
import { QueryApi } from "../../utils/api/QueryApi";
import { memberTokenState } from "../../state/member/MemberState";

const Certification = () => {
  const [certifications, setCertifications] = useRecoilState(raceMissionState);
  const [currentTime, setCurrentTime] = useState(new Date());
  const token = useRecoilValue(memberTokenState);

  useFocusEffect(
    useCallback(() => {
      const intervalId = setInterval(() => setCurrentTime(new Date()), 1000);
      (async () => {
        const { upcoming_missions } = await QueryApi.getMissions(token);
        setCertifications(upcoming_missions);
      })();
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
