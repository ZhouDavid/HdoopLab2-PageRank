#include <iostream>
#include <fstream>
#include <vector>
#include <cstdio>
#include <string>
#include <algorithm>
using namespace std;
double parsePR(const string pr){
	double ans = 0;
	sscanf(pr.c_str(),"%lf",&ans);
	return ans;
}
class Page{
	public:
		string line;
		double pr;
		Page(string line,double pr):line(line),pr(pr){}
};

vector<Page> pages;
bool comp(const Page& p1,const Page& p2){
	return (p1.pr>p2.pr);
}
int main(){
	ifstream fin("part-r-00000");
	string line;
	string pr;
	//read
	while(getline(fin,line)){
		int start = 0;
		start = line.find("|||@");
		if(start!=-1){
			pr = line.substr(start+4,line.length());
			double PR = parsePR(pr);
			pages.push_back(Page(line,PR));
		}
		else{
			int start2 = line.find(">>>>>@");
			if(start2!=-1)
			pr = line.substr(start2+6,line.length());
			double PR = parsePR(pr);
			pages.push_back(Page(line,PR));
		}
	}
	
	//sort
	sort(pages.begin(),pages.end(),comp);
	
	//wirte
	ofstream fout("sorted_part-r-00000");
	for(int i = 0;i<pages.size();i++){
		fout<<pages[i].line<<endl;
	}
	fin.close();
	fout.close();
	return 0;
} 

