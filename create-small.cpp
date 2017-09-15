#include <iostream>
#include <fstream>
#include <string>
#include <set>

using namespace std;

void generateTestFile()
{
    ifstream userRatingsFile("./data/yahoo-track2/trainIdx2.txt", ios::in);
    ofstream userRatingsSmallFile("./data/small-test/test.txt", ios::out);
    set<int> trackIds;
    std::set<int>::iterator it;
    string userRatingLine;
    int nUsers = 0, userId, nRatings, n, score, trackId;

    if (userRatingsFile.is_open()) {
        while(nUsers < 100) {
            getline(userRatingsFile, userRatingLine);
            sscanf(userRatingLine.c_str(), "%d|%d", &userId, &nRatings);
            userRatingsSmallFile << userRatingLine << endl;

            n = 0;
            while (n < nRatings) {
                getline(userRatingsFile, userRatingLine);
                sscanf(userRatingLine.c_str(), "%d %d", &trackId, &score);
                trackIds.insert(trackId);
                userRatingsSmallFile << userRatingLine << endl;
                n ++;
            }

            nUsers ++;
        }
    }

    ofstream trackIdsFile("./data/small-test/trackData.txt", ios::out);

    for (it = trackIds.begin(); it != trackIds.end(); ++it){
        int trackId = *it;
        trackIdsFile << trackId << endl;
    }

    userRatingsFile.close();
    userRatingsSmallFile.close();
    trackIdsFile.close();
}

int main()
{
    generateTestFile();
}

