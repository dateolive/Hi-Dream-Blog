import {
	SAVE_SITE_INFO,
	SAVE_INTRODUCTION,
	SET_IS_BLOG_RENDER_COMPLETE,
} from "./mutations-types";


export default {
	saveSiteInfo({commit}, siteInfo) {
		commit(SAVE_SITE_INFO, {siteInfo})
	},
	saveIntroduction({commit}, introduction) {
		commit(SAVE_INTRODUCTION, {introduction})
	},
	setIsBlogRenderComplete({commit}, ok) {
		commit(SET_IS_BLOG_RENDER_COMPLETE, {ok})
	}
}