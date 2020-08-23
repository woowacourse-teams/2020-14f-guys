import React, { useEffect, useState } from "react";
import { ScrollView, StyleSheet, Text, View } from "react-native";
import { ACHIEVEMENT_COLORS, COLOR } from "../../../utils/constants";
import { QueryApi } from "../../../utils/api/QueryApi";
import { useRecoilState, useRecoilValue } from "recoil/dist";
import { memberInfoState, memberTokenState, } from "../../../state/member/MemberState";
import { achievementRatesState } from "../../../state/certification/AchievementRatesState";

const AchievementItems = () => {
  const token = useRecoilValue(memberTokenState);
  const member = useRecoilValue(memberInfoState);
  const [achievementRates, setAchievementRates] = useRecoilState(
    achievementRatesState
  );

  const [isCalculated, setIsCalculated] = useState(false);

  const getRandomColor = (index) => {
    ACHIEVEMENT_COLORS[index % ACHIEVEMENT_COLORS.length];
  };

  useEffect(() => {
    setIsCalculated(false);
    const fetchRaceAchievementRate = async () => {
      try {
        const { race_responses: races } = await QueryApi.getRaces(token);
        console.log(races.length);
        await races.map((race) => {
          QueryApi.getRaceAchievement(token, race.id)
            .then((achievement) => {
              setAchievementRates([
                ...achievementRates,
                {
                  race_id: achievement.race_id,
                  race_title: achievement.race_title,
                  total_mission_count: achievement.total_mission_count,
                  achievement: achievement.race_achievement_rates.filter(
                    (rate) => rate.member_id === member.id
                  )[0].achievement,
                },
              ]);
            })
            .catch((e) => console.log(e.response.data.message));
        });
      } catch (e) {
        console.log(e.response.data.message);
      }
    };
    fetchRaceAchievementRate().then(() => console.log(achievementRates));
  }, []);

  return (
    <ScrollView horizontal={true} contentContainerStyle={styles.container}>
      {isCalculated ? (
        <View>
          {/*{achievementRates.map((achievementRate, index) => (*/}
          {/*  <AchievementItem*/}
          {/*    key={achievementRates.race_id}*/}
          {/*    ratio={achievementRate.achievement}*/}
          {/*    raceInitial={achievementRate.race_title.substring(0, 1)}*/}
          {/*    color={getRandomColor(index)}*/}
          {/*  />*/}
          {/*))}*/}
        </View>
      ) : (
        <View style={styles.loadingTextContainer}>
          <Text style={styles.loadingText}>잠시만 기다려주세요!</Text>
        </View>
      )}
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    marginHorizontal: 20,
    marginVertical: 10,
  },
  title: {
    paddingTop: 35,
    paddingLeft: 50,
    fontSize: 18,
    fontWeight: "600",
  },
  loadingTextContainer: {
    alignItems: "center",
    justifyContent: "center",
    height: "100%",
  },
  loadingText: {
    color: COLOR.GRAY7,
  },
});

export default AchievementItems;
