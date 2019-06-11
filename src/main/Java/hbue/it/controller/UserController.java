package hbue.it.controller;

import hbue.it.pojo.*;
import hbue.it.service.Facade.ContestFacadeService;
import hbue.it.service.Facade.SignUpFacadeService;
import hbue.it.util.Page;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@Controller
public class UserController extends BaseController {
    @Autowired
    private ContestFacadeService contestFacadeService;

    @Autowired
    private SignUpFacadeService signUpFacadeService;

    @Autowired
    private Producer kaptchaProducer;


    @RequestMapping("listMyContest")
    public String listMyContest(Model model, int start) {
        User user = (User) session.getAttribute("user");
        List<Contest> list = contestFacadeService.listContestByid(user.getId(), start);
        model.addAttribute("list", list);
        Page page = new Page(list, start);
        model.addAttribute("page", page);
        return "admin/myConpetition";
    }

    @RequestMapping("editMyContest")
    public String editMyContestInfo(int cid, Model model, int status) {
        User user = (User) session.getAttribute("user");
        List<Column_info> list = signUpFacadeService.listAllInfo(cid);
        status = contestFacadeService.judgeUCStatus(user.getId(), cid, status);
        model.addAttribute("list", list);
        model.addAttribute("status", status);
        model.addAttribute("cid", cid);
        return "admin/editRegistrationInformation";
    }

    /**
     * @param contest
     * @return
     * @throws IOException
     */
    @RequestMapping("downLoad")
    public ResponseEntity<byte[]> testResponseEntity(Contest contest) throws IOException {
        String attachmentId = String.valueOf(contest.getAttachment());
        String fileName = attachmentId + "_" + contest.getName() + ".rar";
        String oldName = contest.getName() + ".rar";
        ServletContext context = session.getServletContext();
        String path = context.getRealPath("/File/");
        File file = new File(path + File.separator + fileName);

        if (!file.exists()) {
            return null;
        }

        HttpHeaders headers = new HttpHeaders();
        if (getBrowser().equals("FF")) {
            headers.setContentDispositionFormData("attachment", new String(oldName.getBytes("UTF-8"), "iso-8859-1"));
        } else {
            headers.setContentDispositionFormData("attachment", URLEncoder.encode(oldName, "UTF-8"));
        }
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        HttpStatus httpStatus = HttpStatus.OK;
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, httpStatus);
        return response;
    }

    @RequestMapping("listContest")
    public String listContests(Model model, int start) {
        User user = (User) session.getAttribute("user");
        List<Contest> list = contestFacadeService.listContests(user, start);
        Page page = new Page(3, start, list);
        model.addAttribute("list", list);
        model.addAttribute("page", page);
        return "admin/Competitions";
    }


    private String getBrowser() {
        String agent = request.getHeader("USER-AGENT").toLowerCase();
        if (agent != null) {
            if (agent.indexOf("msie") > 0) return "IE";
            else if (agent.indexOf("firefox") > 0) return "FF";
            else if (agent.indexOf("safari") > 0) return "SF";
        }
        return null;
    }






    // 使用Kaptcha框架
    @RequestMapping(value = "/Kaptcha")
    public ModelAndView handleRequest() {
        // 清除浏览器缓存
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        //Producer kaptchaProducer = null;
        // 获取Kaptcha验证的随机文本
        String capText = kaptchaProducer.createText();
        // 将图片放入会话中
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        BufferedImage bi = kaptchaProducer.createImage(capText);
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            ImageIO.write(bi, "jpg", out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (out != null) {
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @RequestMapping(value = "/checkKaptcha", method = RequestMethod.POST)
    public String checkKaptcha() {
        // 获取传入的验证码
        String code = request.getParameter("kaptcha_code");
        if (code != null) {
            String sessionCode = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
            System.out.println(code);
            System.out.println(sessionCode);
            if (!sessionCode.isEmpty() && !code.equalsIgnoreCase(sessionCode)) {
                System.out.println("验证成功!");
                return "login";
            } else {
                System.out.println("验证失败!");
            }
        }
        return "login";
    }


}
