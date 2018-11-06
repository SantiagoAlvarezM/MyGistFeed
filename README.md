# MyGistFeed
Demo app that wraps some features of the Github Gits API

![Alt text](MyGistFeed/screenshots/Screenshot_1541506852.png?raw=true "Home")

![Alt text](MyGistFeed/screenshots/Screenshot_1541506884.png?raw=true "Detail")

KNOW ISSUES
1. After logout, resources are not fully cleared, this causes that Login action menu is not updated or have a weird behavior
2. No deterministic - moving through Home tabs recyclerView shows a dataset that doesn't correspond to current category
3. No deterministic - In Detail screen when tap in back navigation don't move from that screen, so have to press back twice, this is may due a CodeView issue
